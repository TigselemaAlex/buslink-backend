package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.exception.CooperativeNotFoundException;
import com.cdg.buslinkbackend.exception.UserNotFoundException;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.mappers.UserMapper;
import com.cdg.buslinkbackend.model.request.user.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.user.BusUserResponseDTO;
import com.cdg.buslinkbackend.model.response.user.UserResponseDTO;
import com.cdg.buslinkbackend.repository.CooperativeRepository;
import com.cdg.buslinkbackend.repository.UserRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * It returns a ResponseEntity of type ApiResponse, which is a wrapper class for
     * the User object
     * 
     * @param id The id of the user to be found
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return getApiResponseResponseEntity(user);
    }

    /**
     * It returns a response entity with a list of users
     * 
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay usuarios");
        }
        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserMapper::userResponseDTOFromUser).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de usuarios", userResponseDTOList);
    }

    /**
     * It returns a list of users with the role of admin
     * 
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findByRoleAsAdmins() {
        Collection<String> roles = roleService.findAllAdmins()
                .stream()
                .map(
                        role -> role.getName().name())
                .toList();
        List<User> userList = userRepository.findByRoleIn(roles);

        if (userList.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay usuarios");
        }
        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserMapper::userResponseDTOFromUser).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de usuarios administradores",
                userResponseDTOList);
    }

    /**
     * It checks if a user exists in the database by their username
     * 
     * @param username The username of the user to check for.
     * @return A boolean value.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * It checks if the user exists in the database by the id
     * 
     * @param id The id of the user to be deleted.
     * @return A boolean value.
     */
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    /**
     * The function receives a user object, checks if the user exists, if it
     * doesn't, it sets the
     * status to true, encodes the password, sets the role, and saves the user
     * 
     * @param user UserRequestDTO
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> saveANT(UserRequestDTO user) {
        if (existsByUsername(user.getUsername())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El usuario ya existe");
        }
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = UserMapper.userFromUserRequestDTO(user);
        String role = roleService.findById(user.getRole_id()).getName().name();
        userSaved.setRole(role);
        userSaved = userRepository.save(userSaved);
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario creado exitosamente",
                userResponseDTO);
    }

    /**
     * The function receives a user object, saves it to the database, and returns a
     * response object
     * 
     * @param busAdminUserRequestDTO This is the object that contains the data that
     *                               is sent from the
     *                               frontend.
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> saveBusUser(BusUserRequestDTO busAdminUserRequestDTO) {
        if (existsByUsername(busAdminUserRequestDTO.getUsername())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El usuario ya existe");
        }
        busAdminUserRequestDTO.setStatus(true);
        busAdminUserRequestDTO.setPassword(passwordEncoder.encode(busAdminUserRequestDTO.getPassword()));
        User userSaved = UserMapper.userFromBusUserRequestDTO(busAdminUserRequestDTO);
        String role = roleService.findById(busAdminUserRequestDTO.getRole_id()).getName().name();
        Cooperative cooperative = getCooperative(busAdminUserRequestDTO);
        userSaved.setRole(role);
        userSaved.setCooperative(cooperative);
        userSaved = userRepository.save(userSaved);
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario de cooperativa creado exitosamente",
                userResponseDTO);
    }

    /**
     * The function receives a user object and an id, it searches for the user in
     * the database, updates
     * the user with the new data and returns a response
     * 
     * @param user UserRequestDTO
     * @param id   String
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> update(UserRequestDTO user, String id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userToUpdate.setCi(user.getCi());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setUsername(user.getUsername());
        if (Objects.nonNull(user.getPassword())) {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userToUpdate.setStatus(user.isStatus());
        userToUpdate.setFull_name(user.getFull_name());
        String role = roleService.findById(user.getRole_id()).getName().name();
        userToUpdate.setRole(role);
        userToUpdate.setPhone(user.getPhone());
        if (user instanceof BusUserRequestDTO) {
            Cooperative cooperative = getCooperative(((BusUserRequestDTO) user));
            userToUpdate.setCooperative(cooperative);
            User userSaved = userRepository.save(userToUpdate);
            BusUserResponseDTO busUserResponseDTO = UserMapper.busUserResponseDTOFromUser(userSaved);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(),
                    "Usuario de cooperativa actualizado exitosamente", busUserResponseDTO);
        }
        User userSaved = userRepository.save(userToUpdate);
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario actualizado exitosamente",
                userResponseDTO);
    }

    /**
     * If the user exists, delete it, otherwise throw an exception
     * 
     * @param id The id of the user to be deleted
     * @return A ResponseEntity object.
     */
    @Override
    public ResponseEntity<ApiResponse> deleteById(String id) {
        if (existsById(id)) {
            userRepository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Usuario eliminado exitosamente");
        }
        throw new UserNotFoundException(id);
    }

    /**
     * It returns a ResponseEntity of type ApiResponse, which is a wrapper class for
     * the User object
     * 
     * @param username The username of the user you want to find.
     * @return A ResponseEntity object with a body of type ApiResponse.
     */
    @Override
    public ResponseEntity<ApiResponse> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return getApiResponseResponseEntity(user);
    }

    /**
     * If the user has a cooperative, then return a bus user response DTO, otherwise
     * return a user
     * response DTO
     * 
     * @param user User
     * @return A ResponseEntity<ApiResponse>
     */
    private ResponseEntity<ApiResponse> getApiResponseResponseEntity(User user) {
        if (Objects.nonNull(user.getCooperative())) {
            BusUserResponseDTO busUserResponseDTO = UserMapper.busUserResponseDTOFromUser(user);
            return responseBuilder
                    .buildResponse(HttpStatus.OK.value(), "Usuario de cooperativa encontrado", busUserResponseDTO);
        }
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(user);
        return responseBuilder
                .buildResponse(HttpStatus.OK.value(), "Usuario encontrado", userResponseDTO);
    }

    /**
     * It gets a cooperative from the database using the id provided in the request
     * 
     * @param busAdminUserRequestDTO This is the object that contains the parameters
     *                               that are passed to
     *                               the API.
     * @return A Cooperative object.
     */
    private Cooperative getCooperative(BusUserRequestDTO busAdminUserRequestDTO) {
        Cooperative cooperative = cooperativeRepository
                .findById(busAdminUserRequestDTO.getCoop_id())
                .orElseThrow(() -> new CooperativeNotFoundException(busAdminUserRequestDTO.getCoop_id()));
        return cooperative;
    }
}

package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.exception.UserNotFoundException;
import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.mappers.UserMapper;
import com.cdg.buslinkbackend.model.request.user.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.user.BusUserResponseDTO;
import com.cdg.buslinkbackend.model.response.user.UserResponseDTO;
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
    private ResponseBuilder responseBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(Objects.nonNull(user.getCoop_id())){
            BusUserResponseDTO busUserResponseDTO = UserMapper.busUserResponseDTOFromUser(user);
            return responseBuilder
                    .buildResponse(HttpStatus.OK.value(), "Usuario de cooperativa encontrado", busUserResponseDTO);
        }
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(user);
        return responseBuilder
                .buildResponse(HttpStatus.OK.value(), "Usuario encontrado", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay usuarios");
        }
        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserMapper::userResponseDTOFromUser).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de usuarios", userResponseDTOList);
    }

    @Override
    public ResponseEntity<ApiResponse> findByRoleAsAdmins(){
        Collection<String> roles = roleService.findAllAdmins()
                .stream()
                .map(
                        role -> role.getName().name()
                ).toList();
        List<User> userList = userRepository.findByRoleIn(roles);

        if(userList.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay usuarios");
        }
        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserMapper::userResponseDTOFromUser).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de usuarios administradores", userResponseDTOList);
    }



    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

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
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario creado exitosamente", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> saveBusUser(BusUserRequestDTO busAdminUserRequestDTO) {
        if (existsByUsername(busAdminUserRequestDTO.getUsername())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El usuario ya existe");
        }
        busAdminUserRequestDTO.setStatus(true);
        busAdminUserRequestDTO.setPassword(passwordEncoder.encode(busAdminUserRequestDTO.getPassword()));
        User userSaved = UserMapper.userFromBusUserRequestDTO(busAdminUserRequestDTO);
        String role = roleService.findById(busAdminUserRequestDTO.getRole_id()).getName().name();
        String coop = "None"; //Cambiar cuando se tenga los servicios de las cooperativas
        userSaved.setRole(role);
        userSaved.setCoop_id(coop);
        userSaved = userRepository.save(userSaved);
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario de cooperativa creado exitosamente", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> update(UserRequestDTO user, String id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userToUpdate.setCi(user.getCi());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setStatus(user.isStatus());
        userToUpdate.setFull_name(user.getFull_name());
        String role = roleService.findById(user.getRole_id()).getName().name();
        userToUpdate.setRole(role);
        userToUpdate.setPhone(user.getPhone());
        if(user instanceof  BusUserRequestDTO){
            userToUpdate.setCoop_id(((BusUserRequestDTO) user).getCoop_id());
            User userSaved = userRepository.save(userToUpdate);
            BusUserResponseDTO busUserResponseDTO = UserMapper.busUserResponseDTOFromUser(userSaved);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario de cooperativa actualizado exitosamente", busUserResponseDTO);
        }
        User userSaved = userRepository.save(userToUpdate);
        UserResponseDTO userResponseDTO = UserMapper.userResponseDTOFromUser(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario actualizado exitosamente", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteById(String id) {
        if (existsById(id)) {
            userRepository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Usuario eliminado exitosamente");
        }
        throw new UserNotFoundException(id);
    }


}

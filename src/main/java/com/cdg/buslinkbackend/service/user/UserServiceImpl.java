package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.exception.UserNotFoundException;
import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.mappers.UserMapper;
import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.UserResponseDTO;
import com.cdg.buslinkbackend.repository.UserRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResponseBuilder responseBuilder;


    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserResponseDTO userResponseDTO = UserMapper.from(user);
        return responseBuilder
                .buildResponse(HttpStatus.OK.value(), "Usuario encontrado", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay usuarios");
        }
        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserMapper::from).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de usuarios", userResponseDTOList);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public ResponseEntity<ApiResponse> save(UserRequestDTO user) {
        if(existsByUsername(user.getUsername())){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El usuario ya existe");
        }
        user.setStatus(true);
        User userSaved = UserMapper.from(user);
        String role = roleService.findById(user.getRole_id()).getName().name();
        userSaved.setRole(role);
        userSaved = userRepository.save(userSaved);
        UserResponseDTO userResponseDTO = UserMapper.from(userSaved);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Usuario creado exitosamente", userResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> update(UserRequestDTO user, String id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userToUpdate.setCi(user.getCi());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setStatus(user.isStatus());
        userToUpdate.setFull_name(user.getFull_name());
        String role = roleService.findById(user.getRole_id()).getName().name();
        userToUpdate.setRole(role);
        userToUpdate.setPhone(user.getPhone());
        User userSaved = userRepository.save(userToUpdate);
        UserResponseDTO userResponseDTO = UserMapper.from(userSaved);
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
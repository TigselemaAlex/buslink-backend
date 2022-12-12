package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.UserResponseDTO;

public class UserMapper {
    public static UserResponseDTO from(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .ci(user.getCi())
                .phone(user.getPhone())
                .username(user.getUsername())
                .status(user.isStatus())
                .city(user.getCity())
                .full_name(user.getFull_name())
                .role(user.getRole())
                .build();
    }

    public static User from(UserRequestDTO userRequestDTO){
        return User.builder()
                .ci(userRequestDTO.getCi())
                .phone(userRequestDTO.getPhone())
                .username(userRequestDTO.getUsername())
                .status(userRequestDTO.isStatus())
                .city(userRequestDTO.getCity())
                .full_name(userRequestDTO.getFull_name())
                .password(userRequestDTO.getPassword())
                .build();
    }
}

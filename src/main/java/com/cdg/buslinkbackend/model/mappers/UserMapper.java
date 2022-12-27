package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.request.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.UserResponseDTO;
import com.cdg.buslinkbackend.security.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class UserMapper {
    public static UserResponseDTO userResponseDTOFromUser(User user){
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

    public static User userFromUserRequestDTO(UserRequestDTO userRequestDTO){
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

    public static UserPrincipal userPrincipalFromUser(User user){
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        return UserPrincipal.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.isStatus())
                .authorities(authorities)
                .build();
    }


}

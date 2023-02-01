package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.request.user.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserRequestDTO;
import com.cdg.buslinkbackend.model.response.user.BusUserResponseDTO;
import com.cdg.buslinkbackend.model.response.user.UserResponseDTO;
import com.cdg.buslinkbackend.security.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * > This class is a mapper that maps a user to a userDTO
 */
public class UserMapper {
    /**
     * It takes a User object and returns a UserResponseDTO object
     * 
     * @param user The user object that you want to convert to a UserResponseDTO
     *             object.
     * @return A UserResponseDTO object
     */
    public static UserResponseDTO userResponseDTOFromUser(User user) {
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

    /**
     * It takes a User object and returns a BusUserResponseDTO object
     * 
     * @param user User
     * @return A BusUserResponseDTO object
     */
    public static BusUserResponseDTO busUserResponseDTOFromUser(User user) {
        return BusUserResponseDTO.builder()
                .id(user.getId())
                .ci(user.getCi())
                .phone(user.getPhone())
                .username(user.getUsername())
                .status(user.isStatus())
                .city(user.getCity())
                .full_name(user.getFull_name())
                .role(user.getRole())
                .cooperative(CooperativeMapper.cooperativeResponseDTOFromCooperative(user.getCooperative()))
                .build();

    }

    /**
     * It takes a UserRequestDTO object and returns a User object
     * 
     * @param userRequestDTO This is the object that contains the data that will be
     *                       used to create the
     *                       user.
     * @return A User object
     */
    public static User userFromUserRequestDTO(UserRequestDTO userRequestDTO) {
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

    /**
     * It takes a BusUserRequestDTO object and returns a User object
     * 
     * @param busUserRequestDTO This is the object that is passed to the method.
     * @return A User object
     */
    public static User userFromBusUserRequestDTO(BusUserRequestDTO busUserRequestDTO) {
        return User.builder()
                .ci(busUserRequestDTO.getCi())
                .phone(busUserRequestDTO.getPhone())
                .username(busUserRequestDTO.getUsername())
                .status(busUserRequestDTO.isStatus())
                .city(busUserRequestDTO.getCity())
                .full_name(busUserRequestDTO.getFull_name())
                .password(busUserRequestDTO.getPassword())
                .build();
    }

    /**
     * It takes a user object and returns a UserPrincipal object
     * 
     * @param user The user object that we are converting to UserPrincipal
     * @return A UserPrincipal object
     */
    public static UserPrincipal userPrincipalFromUser(User user) {
        List<SimpleGrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole()));
        return UserPrincipal.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.isStatus())
                .authorities(authorities)
                .build();
    }

}

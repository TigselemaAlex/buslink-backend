package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<ApiResponse> findById(String id);

    ResponseEntity<ApiResponse> findAll();

    ResponseEntity<ApiResponse> save(UserRequestDTO user);

    ResponseEntity<ApiResponse> update(UserRequestDTO user, String id);

   ResponseEntity<ApiResponse> deleteById(String id);
}

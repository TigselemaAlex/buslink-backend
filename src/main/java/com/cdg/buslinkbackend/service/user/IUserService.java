package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.model.request.user.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<ApiResponse> findById(String id);
    ResponseEntity<ApiResponse> findAll();
    ResponseEntity<ApiResponse> findByRoleAsAdmins();
    ResponseEntity<ApiResponse> saveANT(UserRequestDTO user);
    ResponseEntity<ApiResponse> saveBusUser(BusUserRequestDTO busAdminUserRequestDTO);
    ResponseEntity<ApiResponse> update(UserRequestDTO user, String id);
   ResponseEntity<ApiResponse> deleteById(String id);
   ResponseEntity<ApiResponse> findByUsername(String username);
}

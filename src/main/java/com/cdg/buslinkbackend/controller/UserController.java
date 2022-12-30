package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.service.user.UserServiceImpl;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/protected/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/admins")
    public ResponseEntity<ApiResponse> findAllRoleAsAdmins() {
        return userService.findByRoleAsAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@NotBlank(message = "El id no puede estar vacio") @PathVariable final String id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveANT(@Valid @RequestBody final UserRequestDTO userRequestDTO) {
        return userService.saveANT(userRequestDTO);
    }

    @PostMapping("/bus_user")
    public ResponseEntity<ApiResponse> saveBusUser(@Valid @RequestBody final BusUserRequestDTO userRequestDTO) {
        return userService.saveBusUser(userRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@NotBlank(message = "El id no puede estar vacio") @PathVariable final String id, @Valid @RequestBody final UserRequestDTO userRequestDTO) {
        return userService.update(userRequestDTO, id);
    }

    @PutMapping("/bus_user/{id}")
    public ResponseEntity<ApiResponse> updateBusUser(@NotBlank(message = "El id no puede estar vacio") @PathVariable final String id, @Valid @RequestBody final BusUserRequestDTO userRequestDTO) {
        return userService.update(userRequestDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@NotBlank(message = "El id no puede estar vacio") @PathVariable final String id) {
        return userService.deleteById(id);
    }

}

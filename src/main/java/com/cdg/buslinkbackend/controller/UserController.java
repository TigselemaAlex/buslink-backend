package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.user.BusUserRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserRequestDTO;
import com.cdg.buslinkbackend.service.user.ClientService;
import com.cdg.buslinkbackend.service.user.UserServiceImpl;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/protected/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ClientService clientService;

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

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> findByUsername(@PathVariable final String username){
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
                "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(username);
        if(matcher.matches()){

            return clientService.findByEmail(username);
        }
        return userService.findByUsername(username);
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

    @GetMapping(value = "/client/ci/{ci}")
    public ResponseEntity<ApiResponse> findByCi(@PathVariable final String ci){
        return clientService.findByCedula(ci);
    }

}

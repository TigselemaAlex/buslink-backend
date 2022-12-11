package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.UserRequestDTO;
import com.cdg.buslinkbackend.service.user.UserServiceImpl;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/protected/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id){
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody final UserRequestDTO userRequestDTO){
        return userService.save(userRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable final String id,@RequestBody final UserRequestDTO userRequestDTO){
        return userService.update(userRequestDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable final String id){
        return userService.deleteById(id);
    }

}

package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/protected/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<Role> roleList = roleService.findAll();
        response.put("roles", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

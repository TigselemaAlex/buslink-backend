package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/admins")
    public ResponseEntity<Map<String, Object>> findAllAdmins(){
        Map<String, Object> response = new HashMap<>();
        List<Role> roleList = (List<Role>) roleService.findAllAdmins();
        response.put("roles", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bus")
    public ResponseEntity<Map<String, Object>> findAllBusUsers(){
        Map<String, Object> response = new HashMap<>();
        List<Role> roleList = (List<Role>) roleService.findAllBusUsers();
        response.put("roles", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable final String id){
        return ResponseEntity.ok(roleService.findById(id));
    }
}

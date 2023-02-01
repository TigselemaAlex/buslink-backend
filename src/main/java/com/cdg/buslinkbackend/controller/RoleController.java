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

/**
 * It's a controller that handles all the requests that are related to the role
 * entity.
 */
@RestController
@RequestMapping("/protected/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * It returns a list of all the admins in the database
     * 
     * @return A list of all the admins.
     */
    @GetMapping("/admins")
    public ResponseEntity<Map<String, Object>> findAllAdmins() {
        Map<String, Object> response = new HashMap<>();
        List<Role> roleList = (List<Role>) roleService.findAllAdmins();
        response.put("roles", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This function returns a list of all the users that have the role of
     * 'BUS_USER' in the database.
     * 
     * @return A list of roles
     */
    @GetMapping("/bus")
    public ResponseEntity<Map<String, Object>> findAllBusUsers() {
        Map<String, Object> response = new HashMap<>();
        List<Role> roleList = (List<Role>) roleService.findAllBusUsers();
        response.put("roles", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * It returns a response entity with a status code of 200 and a body of the role
     * object that was
     * found by the id
     * 
     * @param id The id of the role you want to find.
     * @return A ResponseEntity object with a Role object inside.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable final String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }
}

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

/**
 * This class is a controller that handles requests to the /protected/users
 * endpoint.
 */
@RestController
@RequestMapping(value = "/protected/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ClientService clientService;

    /**
     * This function returns a response entity that contains an API response that
     * contains a list of
     * users.
     * 
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return userService.findAll();
    }

    /**
     * This function returns a list of all users with the role of admin.
     * 
     * @return A list of users with the role of admin.
     */
    @GetMapping("/admins")
    public ResponseEntity<ApiResponse> findAllRoleAsAdmins() {
        return userService.findByRoleAsAdmins();
    }

    /**
     * It returns a response entity with an api response object
     * 
     * @param id The id of the user to be retrieved.
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(
            @NotBlank(message = "El id no puede estar vacio") @PathVariable final String id) {
        return userService.findById(id);
    }

    /**
     * It checks if the username is an email, if it is, it returns the
     * clientService.findByEmail(username) function, if it isn't, it returns the
     * userService.findByUsername(username) function
     * 
     * @param username the username of the user
     * @return A ResponseEntity object.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> findByUsername(@PathVariable final String username) {
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
                "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {

            return clientService.findByEmail(username);
        }
        return userService.findByUsername(username);
    }

    /**
     * This function takes a UserRequestDTO object, validates it, and then passes it
     * to the
     * userService.saveANT() function.
     * 
     * @param userRequestDTO This is the object that is being passed in the request
     *                       body.
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveANT(@Valid @RequestBody final UserRequestDTO userRequestDTO) {
        return userService.saveANT(userRequestDTO);
    }

    /**
     * It takes a BusUserRequestDTO object as a parameter, validates it, and then
     * passes it to the
     * userService.saveBusUser() function.
     * 
     * @param userRequestDTO This is the object that will be sent to the server.
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping("/bus_user")
    public ResponseEntity<ApiResponse> saveBusUser(@Valid @RequestBody final BusUserRequestDTO userRequestDTO) {
        return userService.saveBusUser(userRequestDTO);
    }

    /**
     * It updates a user by id
     * 
     * @param id             The id of the user to be updated
     * @param userRequestDTO This is the object that will be sent in the request
     *                       body.
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @NotBlank(message = "El id no puede estar vacio") @PathVariable final String id,
            @Valid @RequestBody final UserRequestDTO userRequestDTO) {
        return userService.update(userRequestDTO, id);
    }

    /**
     * It updates a user in the database
     * 
     * @param id             The id of the user to be updated
     * @param userRequestDTO This is the object that I want to update.
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/bus_user/{id}")
    public ResponseEntity<ApiResponse> updateBusUser(
            @NotBlank(message = "El id no puede estar vacio") @PathVariable final String id,
            @Valid @RequestBody final BusUserRequestDTO userRequestDTO) {
        return userService.update(userRequestDTO, id);
    }

    /**
     * It deletes a user by id
     * 
     * @param id The id of the user to be deleted.
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(
            @NotBlank(message = "El id no puede estar vacio") @PathVariable final String id) {
        return userService.deleteById(id);
    }

    /**
     * It receives a ci (ciudadania) and returns a response entity with a client
     * object
     * 
     * @param ci is the identification number of the client
     * @return A ResponseEntity&lt;ApiResponse&gt;
     */
    @GetMapping(value = "/client/ci/{ci}")
    public ResponseEntity<ApiResponse> findByCi(@PathVariable final String ci) {
        return clientService.findByCedula(ci);
    }

}

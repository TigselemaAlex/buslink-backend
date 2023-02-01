package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.user.ClientLoginRequestDTO;
import com.cdg.buslinkbackend.model.request.user.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.request.user.UserLoginRequestDTO;
import com.cdg.buslinkbackend.security.jwt.JWTProvider;
import com.cdg.buslinkbackend.security.jwt.JWTResponse;
import com.cdg.buslinkbackend.service.user.ClientService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * It's a controller that has two methods: one for logging in, and one for
 * registering.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private ClientService clientService;

    /**
     * Authenticate the user and return a response entity with the authentication
     * token.
     * 
     * @param userLoginRequestDTO This is a DTO class that contains the username and
     *                            password.
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> authenticateUser(
            @Valid @RequestBody final UserLoginRequestDTO userLoginRequestDTO)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(),
                        userLoginRequestDTO.getPassword()));
        return getApiResponseResponseEntity(authentication);
    }

    // A method that is called when the user tries to log in. It takes a
    // ClientLoginRequestDTO object
    // as a parameter, and returns a ResponseEntity<ApiResponse> object.
    @PostMapping("/signin/client")
    public ResponseEntity<ApiResponse> authenticateUser(
            @Valid @RequestBody final ClientLoginRequestDTO clientLoginRequestDTO)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(clientLoginRequestDTO.getEmail(),
                        clientLoginRequestDTO.getPassword()));
        return getApiResponseResponseEntity(authentication);
    }

    /**
     * It sets the authentication in the security context, generates a JWT token,
     * gets the user details
     * from the authentication, creates a JWT response object and returns a response
     * entity with the
     * JWT response object
     * 
     * @param authentication The authentication object that was created by the
     *                       authentication manager.
     * @return A ResponseEntity object with the following properties:
     *         - Status: 200
     *         - Message: "Usuario logeado exitosamente"
     *         - Body: JWTResponse object with the following properties:
     *         - jwt: The JWT token
     *         - username: The username
     *         - authorities: The authorities
     */
    private ResponseEntity<ApiResponse> getApiResponseResponseEntity(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JWTResponse jwtResponse = new JWTResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Usuario logeado exitosamente", jwtResponse);
    }

    /**
     * It takes a ClientRegisterRequestDTO object as a parameter, and returns a
     * ResponseEntity<ApiResponse> object
     * 
     * @param clientRegisterRequestDTO This is the object that will be sent to the
     *                                 server.
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping("/singup/client")
    public ResponseEntity<ApiResponse> registerClient(
            @Valid @RequestBody final ClientRegisterRequestDTO clientRegisterRequestDTO) {
        return clientService.registerClient(clientRegisterRequestDTO);
    }
}

package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.ClientLoginRequestDTO;
import com.cdg.buslinkbackend.model.request.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.request.UserLoginRequestDTO;
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


    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody final UserLoginRequestDTO userLoginRequestDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));
        return getApiResponseResponseEntity(authentication);
    }


    @PostMapping("/signin/client")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody final ClientLoginRequestDTO clientLoginRequestDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(clientLoginRequestDTO.getEmail(), clientLoginRequestDTO.getPassword()));
        return getApiResponseResponseEntity(authentication);
    }

    private ResponseEntity<ApiResponse> getApiResponseResponseEntity(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JWTResponse jwtResponse = new JWTResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Usuario logeado exitosamente", jwtResponse);
    }


    @PostMapping("/singup/client")
    public ResponseEntity<ApiResponse> registerClient(@Valid @RequestBody final ClientRegisterRequestDTO clientRegisterRequestDTO) {
        return clientService.registerClient(clientRegisterRequestDTO);
    }
}

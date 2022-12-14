package com.cdg.buslinkbackend.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class JWTResponse {

    private String token;
    private final String TOKEN_HEADER ="Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

}
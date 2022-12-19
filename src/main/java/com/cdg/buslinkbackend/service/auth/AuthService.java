package com.cdg.buslinkbackend.service.auth;

import com.cdg.buslinkbackend.exception.UserNotFoundException;
import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.mappers.UserMapper;
import com.cdg.buslinkbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return UserMapper.userPrincipalFromUser(user);
    }
}

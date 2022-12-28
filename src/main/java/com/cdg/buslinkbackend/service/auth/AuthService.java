package com.cdg.buslinkbackend.service.auth;

import com.cdg.buslinkbackend.exception.ClientNotFoundException;
import com.cdg.buslinkbackend.exception.UserNotFoundException;
import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.entity.User;
import com.cdg.buslinkbackend.model.mappers.ClientMapper;
import com.cdg.buslinkbackend.model.mappers.UserMapper;
import com.cdg.buslinkbackend.repository.ClientRepository;
import com.cdg.buslinkbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
                "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(username);
        if(matcher.matches()){
            Client client = clientRepository.findByEmail(username).orElseThrow(()-> new ClientNotFoundException(username));
            return ClientMapper.userPrincipalFromUser(client);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return UserMapper.userPrincipalFromUser(user);
    }


}

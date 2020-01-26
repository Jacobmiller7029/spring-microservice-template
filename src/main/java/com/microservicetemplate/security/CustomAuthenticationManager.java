package com.microservicetemplate.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = userService.loadUserByUsername(authentication.getPrincipal().toString());

        boolean isValidPassword = passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword());

        if(isValidPassword) {
            return authentication;
        }

        throw new UsernameNotFoundException("Invalid password");
    }
}

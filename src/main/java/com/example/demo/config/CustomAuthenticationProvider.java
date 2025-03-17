package com.example.demo.config;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final UserService userService;
    @Autowired
	public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;        
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        try {
            User user = userService.findByEmail(email);
            
            if (!user.getActive()) {
                throw new DisabledException("User account is disabled");
            }
            
            if (!userService.checkPassword(user, password)) {
                throw new BadCredentialsException("Invalid credentials");
            }
            
            // Convert Set<UserRole> to List<GrantedAuthority>
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
            
            // Update last login time
            userService.updateLastLogin(user);
            
            System.out.println("Authenticated User: " + email);
            System.out.println("Authorities: " + authorities);

            
//            return new UsernamePasswordAuthenticationToken(email, password, authorities);
            return new UsernamePasswordAuthenticationToken(user, password, authorities);

            
        } catch (Exception e) {
            throw new BadCredentialsException("Authentication failed: " + e.getMessage());
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
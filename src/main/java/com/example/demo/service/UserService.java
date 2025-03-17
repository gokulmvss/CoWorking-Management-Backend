package com.example.demo.service;

//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    User findByEmail(String email);
    
    Optional<User> findOptionalByEmail(String email);
    
    User findById(Long id);
    
    List<User> findByRole(String roleName);
    
    User saveUser(User user);
    
    void updateLastLogin(User user);
    
    void updatePassword(User user, String newPassword);
    
    void deactivateUser(Long userId);
    
    void activateUser(Long userId);
    
    boolean checkPassword(User user, String rawPassword);
}

//@Service
//@RequiredArgsConstructor
//public class UserService {
//	
//	
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Autowired
//	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//    
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
//    }
//    
//    public Optional<User> findOptionalByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//    
//    public User findById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
//    }
//    
//    @Transactional
//    public User saveUser(User user) {
//        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        return userRepository.save(user);
//    }
//    
//    @Transactional
//    public void updateLastLogin(User user) {
//        user.setUpdatedAt(LocalDateTime.now());
//        userRepository.save(user);
//    }
//    
//    @Transactional
//    public void updatePassword(User user, String newPassword) {
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }
//    
//    @Transactional
//    public void deactivateUser(Long userId) {
//        User user = findById(userId);
//        user.setActive(false);
//        userRepository.save(user);
//    }
//    
//    @Transactional
//    public void activateUser(Long userId) {
//        User user = findById(userId);
//        user.setActive(true);
//        userRepository.save(user);
//    }
//    
//    public boolean checkPassword(User user, String rawPassword) {
//        return passwordEncoder.matches(rawPassword, user.getPassword());
//    }
//    
//    
//}

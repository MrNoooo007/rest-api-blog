package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignupDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignupDto signupDto) {
        if(!userRepository.findByUsername(signupDto.getUsername()).isEmpty()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exist !");
        }
        if(!userRepository.findByEmail(signupDto.getEmail()).isEmpty()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exist !");
        }

        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findById(Long.valueOf("1")).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", 1));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        return new ResponseEntity<>("Sign up successfully with email" + user.getEmail(), HttpStatus.OK);
    }

}

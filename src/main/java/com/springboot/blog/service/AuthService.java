package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignupDto;
import com.springboot.blog.payload.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);
    UserDto register(SignupDto signupDto);
}

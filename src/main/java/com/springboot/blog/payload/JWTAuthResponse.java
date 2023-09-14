package com.springboot.blog.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String tokenType = "Bearer";
    private String accessToken;
}

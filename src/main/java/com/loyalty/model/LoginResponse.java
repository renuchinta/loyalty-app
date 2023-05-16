package com.loyalty.model;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class LoginResponse {
    private Long id;
    private String qrCode;
    private String productName;
    private Long productId;
    private String username;
    private User user;
    private String token;
}

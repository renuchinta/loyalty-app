package com.loyalty.dto;

import lombok.Data;

@Data
public class CustomUserResponse {
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private String userType;

    private Long id;
}

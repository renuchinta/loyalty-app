package com.loyalty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String qrCode;
    private String userType;
    private String role;

}

package dev.danvega.jwt.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
}

package com.loyalty.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String qrCode;
    private Long productId;
    private String productName;
    private String productImage;

}

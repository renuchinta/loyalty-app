package com.loyalty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data 
public class LoginRequest {
    private  String username;
    private  String password;
    private String userType;

}

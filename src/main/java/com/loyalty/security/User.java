package com.loyalty.security;


import jakarta.persistence.Entity;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}

package dev.danvega.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "CUSTOMER_USER")
public class Customer {
    @Id
    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;


}

package dev.danvega.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "customerList")
    private List<BusinessUser> businessUserList = new ArrayList<>();

    public void addBusinessUser(BusinessUser businessUser) {
        if (!getBusinessUserList().contains(businessUser)) {
            getBusinessUserList().add(businessUser);
        }
    }



}

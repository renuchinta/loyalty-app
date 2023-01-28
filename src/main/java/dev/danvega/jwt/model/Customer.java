package dev.danvega.jwt.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
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

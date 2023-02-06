package com.loyalty.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class BusinessUser {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;


    private String address;
    private String userName;
    private String phoneNumber;
    private String email;
    private String password;
    private String businessQRId;
    // One business will have one product
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    @JoinTable(name="BUSINESS_CUSTOMER",
            joinColumns=@JoinColumn(name="BUSINESS_ID"),
            inverseJoinColumns=@JoinColumn(name="CUSTOMER_ID"))
    private List<Customer> customerList = new ArrayList<>();

    public void addCustomers(Customer customer) {
        if (!getCustomerList().contains(customer)) {
            getCustomerList().add(customer);
        }
    }

    @Override
    public String toString() {
        return "BusinessUser{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

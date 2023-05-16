package com.loyalty.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

@Entity
@Table(name = "BUSINESS_USER_DETAILS")
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

    @Column
    private Long userId;
    @Column
    private String address;
    private String phoneNumber;
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
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}

package com.loyalty.model;


import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Long businessId;
    private Long productId;
    private Long orderedQuantity;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;


    public void addCustomer(Customer customer){
        this.customer = customer;
    }

}

package com.loyalty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loyalty.dto.BusinessToCustomer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(name = "Customer.getAllBusinessUsers",
                            query = "select c.business_id,c.customer_id from business_customer  c where c.customer_id = :customerId"
                            ,resultSetMapping = "Mapping.BusinessCustomerDTO")
@SqlResultSetMapping(name = "Mapping.BusinessCustomerDTO",
                        classes = @ConstructorResult(targetClass = BusinessToCustomer.class,
                                                    columns = {@ColumnResult(name = "c.business_id",type = Long.class),
                                                                @ColumnResult(name = "c.customer_id",type = Long.class)}))
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
    private String customerQRId;

    @ManyToMany(mappedBy = "customerList")
    @JsonIgnore
    private List<BusinessUser> businessUserList = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public void addCustomerOrders(CustomerOrder customerOrder){
        if(!getCustomerOrders().contains(customerOrder)){
            getCustomerOrders().add(customerOrder);
        }
    }
    public void addBusinessUser(BusinessUser businessUser) {
        if (!getBusinessUserList().contains(businessUser)) {
            getBusinessUserList().add(businessUser);
        }
    }
}

package dev.danvega.jwt.service;

import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.repository.CustomerRespository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRespository customerRespository;

    public Customer saveCustomer(Customer customer){
        return customerRespository.save(customer);

    }
}

package dev.danvega.jwt.service;

import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.model.LoginRequest;
import dev.danvega.jwt.repository.CustomerRespository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRespository customerRespository;

    public CustomerService(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRespository.save(customer);

    }
    public Customer findByUserNameAndPassword(LoginRequest loginRequest){
        return customerRespository.findByUsernameAndPassword(loginRequest.username(),loginRequest.password());
    }

    public Optional<Customer> findById(Long id){
        return customerRespository.findById(id);
    }
}

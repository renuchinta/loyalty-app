package dev.danvega.jwt.controller;

import dev.danvega.jwt.dto.BusinessCustomerDTO;
import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.repository.BusinessUserRepository;
import dev.danvega.jwt.service.BusinessService;
import dev.danvega.jwt.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final BusinessService businessService;
    private final BusinessUserRepository businessUserRepository;

    public CustomerController(CustomerService customerService, BusinessService businessService, BusinessUserRepository businessUserRepository) {
        this.customerService = customerService;
        this.businessService = businessService;
        this.businessUserRepository = businessUserRepository;
    }

    @PostMapping("/customerSingUp")
    public ResponseEntity<Customer> saveBusinessUser(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PostMapping("/enrollCustomerToBusiness")
    public ResponseEntity<HttpStatus> enrollCustomerToBusiness(@RequestBody BusinessCustomerDTO businessCustomerDTO){
        Optional<BusinessUser> businessUser = businessService.findById(businessCustomerDTO.getBusinessID());
        if(businessUser.isPresent()){
            Customer customer = customerService.findById(businessCustomerDTO.getCustomerID()).get();
            businessUser.get().addCustomers(customer);
            customer.addBusinessUser(businessUser.get());
            businessUserRepository.save(businessUser.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

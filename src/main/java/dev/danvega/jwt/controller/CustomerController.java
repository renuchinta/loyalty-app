package dev.danvega.jwt.controller;

import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/customerSingUp")
    public ResponseEntity<Customer> saveBusinessUser(@RequestBody Customer businessUser){
        Customer customer = customerService.saveCustomer(businessUser);
        return ResponseEntity.ok(customer);
    }
}

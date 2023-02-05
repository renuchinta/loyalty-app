package dev.danvega.jwt.controller;

import dev.danvega.jwt.dto.EnrollCustomerToBusiness;
import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.repository.BusinessUserRepository;
import dev.danvega.jwt.service.BusinessService;
import dev.danvega.jwt.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<HttpStatus> enrollCustomerToBusiness(@RequestBody EnrollCustomerToBusiness enrollCustomerToBusiness){
        Optional<BusinessUser> businessUser = businessService.findByBusinessQRId(enrollCustomerToBusiness.getBusinessQRId());
        if(businessUser.isPresent()){
            Customer customer = customerService.findById(enrollCustomerToBusiness.getCustomerID()).get();
            businessUser.get().addCustomers(customer);
            customer.addBusinessUser(businessUser.get());
            businessUserRepository.save(businessUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getAllBusinessForCustomers")
    public List<BusinessUser> getAllBusinessUserForCustomer(@RequestParam Long customerId){
        return customerService.getAllBusinessUsers(customerId);
    }
}

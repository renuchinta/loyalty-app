package com.loyalty.controller;

import com.loyalty.model.BusinessUser;
import com.loyalty.dto.EnrollCustomerToBusiness;
import com.loyalty.model.Customer;
import com.loyalty.repository.BusinessUserRepository;
import com.loyalty.service.BusinessService;
import com.loyalty.service.CustomerService;
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
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PostMapping("/enrollCustomerToBusiness/{customerID}/{businessQRId}")
    public ResponseEntity<HttpStatus> enrollCustomerToBusiness(@PathVariable long customerID, @PathVariable String businessQRId){
        Optional<BusinessUser> businessUser = businessService.findByBusinessQRId(businessQRId);
        if(businessUser.isPresent()){
            Customer customer = customerService.findById(customerID).get();
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


    @GetMapping("/getBusinessDetails")
    public Optional<BusinessUser> getBusinessDetails(@RequestParam Long businessId) {
        return businessService.findById(businessId);
    }

    @GetMapping("/getCustomerByQRId")
    public ResponseEntity<HttpStatus> getCustomerByQrId(@RequestParam String customerByQRId) {
        Boolean customerExists = customerService.getCustomerByQrId(customerByQRId);
        return customerExists ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

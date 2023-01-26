package dev.danvega.jwt.service;

import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.model.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusinessCusomterAdapterService {

    private final BusinessService businessService;
    private final CustomerService customerService;

    public BusinessCusomterAdapterService(BusinessService businessService, CustomerService customerService) {
        this.businessService = businessService;
        this.customerService = customerService;
    }

    public ResponseEntity<HttpStatus> login(LoginRequest loginRequest) {
        if(loginRequest.userType().equalsIgnoreCase("CUSTOMER")){
            Customer customer=  customerService.findByUserNameAndPassword(loginRequest);
            if(customer.getId() != null){
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else{
            BusinessUser businessUser=  businessService.findByUserNameAndPassword(loginRequest);
            if(businessUser.getId()!= null){
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

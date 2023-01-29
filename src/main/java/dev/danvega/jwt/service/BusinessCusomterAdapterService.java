package dev.danvega.jwt.service;

import dev.danvega.jwt.dto.UserDTO;
import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.model.LoginRequest;
import dev.danvega.jwt.model.CustomerOrder;
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
    public ResponseEntity<UserDTO> login(LoginRequest loginRequest) {
        UserDTO userDTO = new UserDTO();
        if(loginRequest.userType().equalsIgnoreCase("CUSTOMER")){
            Customer customer=  customerService.findByUserNameAndPassword(loginRequest);
            userDTO.setEmail(customer.getEmail());
            userDTO.setUsername(customer.getUsername());
            userDTO.setPhoneNumber(customer.getPhoneNumber());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }else{
            BusinessUser businessUser=  businessService.findByUserNameAndPassword(loginRequest);
            userDTO.setEmail(businessUser.getEmail());
            userDTO.setUsername(businessUser.getUserName());
            userDTO.setPhoneNumber(businessUser.getPhoneNumber());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }

    }
}

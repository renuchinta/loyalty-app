package com.loyalty.service;

import com.loyalty.dto.CompleteBusinessUserSignUp;
import com.loyalty.model.*;
import com.loyalty.dto.UserDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessCusomterAdapterService {

    private final BusinessService businessService;
    private final CustomerService customerService;
    private final ProductService productService;

    private final ProductOfferService productOfferService;
    public BusinessCusomterAdapterService(BusinessService businessService, CustomerService customerService, ProductService productService, ProductOfferService productOfferService) {
        this.businessService = businessService;
        this.customerService = customerService;
        this.productService = productService;
        this.productOfferService = productOfferService;
    }
    public ResponseEntity<UserDTO> login(LoginRequest loginRequest) {
        UserDTO userDTO = new UserDTO();
        if(loginRequest.userType().equalsIgnoreCase("CUSTOMER")){
            Customer customer=  customerService.findByUserNameAndPassword(loginRequest);
            userDTO.setId(customer.getId());
            userDTO.setEmail(customer.getEmail());
            userDTO.setUsername(customer.getUsername());
            userDTO.setPhoneNumber(customer.getPhoneNumber());
            userDTO.setQrCode(customer.getPhoneNumber());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }else{
            BusinessUser businessUser=  businessService.findByUserNameAndPassword(loginRequest);
            userDTO.setId(businessUser.getId());
            userDTO.setEmail(businessUser.getEmail());
            userDTO.setUsername(businessUser.getUserName());
            userDTO.setPhoneNumber(businessUser.getPhoneNumber());
            userDTO.setProductId(businessUser.getProduct().getId());
            userDTO.setProductImage(businessUser.getProduct().getProductImage());
            userDTO.setQrCode(businessUser.getBusinessQRId());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }

    }

    public BusinessUser completeBusinessUserSignup(CompleteBusinessUserSignUp completeBusinessUserSignUp) {
        Optional<BusinessUser> business = businessService.findById(completeBusinessUserSignUp.getBusinessUserId());
        if(business.isPresent()){
            Optional<Product> product = productService.findById(completeBusinessUserSignUp.getProductId());
            BusinessUser businessUser = business.get();
            businessUser.setBusinessQRId(completeBusinessUserSignUp.getBusinessQRId());
            businessUser.setProduct(product.get());
            businessService.saveBusinessUser(businessUser);

            Optional<ProductOffer> productOffer = productOfferService.findById(completeBusinessUserSignUp.getProductOfferId());
       //     product.get().addBusinessUser(businessUser);
            product.get().addProductOffer(productOffer.get());
         //   productOffer.get().addProduct(product.get());
            productService.saveProduct(product.get());
        }
        return business.get();
    }
}
package com.loyalty.service;

import com.loyalty.dto.CompleteBusinessUserSignUp;
import com.loyalty.model.*;

import com.loyalty.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessCusomterAdapterService {

    private final BusinessService businessService;
    private final CustomerService customerService;
    private final ProductService productService;

    private final UserRepository userRepository;


    //HibernateJpaConfiguration hibernate;
    
    private final ProductOfferService productOfferService;
    public BusinessCusomterAdapterService(BusinessService businessService, CustomerService customerService, ProductService productService, UserRepository userRepository, ProductOfferService productOfferService) {
        this.businessService = businessService;
        this.customerService = customerService;
        this.productService = productService;
        this.userRepository = userRepository;
        this.productOfferService = productOfferService;
    }
    /*public ResponseEntity<UserDTO> login(LoginRequest loginRequest) {
        UserDTO userDTO = new UserDTO();
        if(loginRequest.getUserType().equalsIgnoreCase("CUSTOMER")){
            Customer customer=  customerService.findByUserNameAndPassword(loginRequest);
            userDTO.setId(customer.getId());
            userDTO.setEmail(customer.getEmail());
            userDTO.setUsername(customer.getUsername());
            userDTO.setPhoneNumber(customer.getPhoneNumber());
            userDTO.setQrCode(customer.getCustomerQRId());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }else{
            BusinessUser businessUser=  businessService.findByUserNameAndPassword(loginRequest);
            userDTO.setId(businessUser.getId());
            userDTO.setPhoneNumber(businessUser.getPhoneNumber());
            userDTO.setQrCode(businessUser.getBusinessQRId() == null ? "" : businessUser.getBusinessQRId());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }

    }
*/
    public boolean completeBusinessUserSignup(CompleteBusinessUserSignUp completeBusinessUserSignUp) {
        Optional<DAOUser> business = userRepository.findById(completeBusinessUserSignUp.getBusinessUserId());
        //Optional<BusinessUser> business = businessService.findById(completeBusinessUserSignUp.getBusinessUserId());
        if(business.isPresent()){
            Optional<Product> product = productService.findById(completeBusinessUserSignUp.getProductId());
            DAOUser daoUser = business.get();
            BusinessUser businessUser = new BusinessUser();
            businessUser.setBusinessQRId(completeBusinessUserSignUp.getBusinessQRId());
            businessUser.setProduct(product.get());
            businessUser.setUserId(daoUser.getId());

            businessService.saveBusinessUser(businessUser);

            Optional<ProductOffer> productOffer = productOfferService.findById(completeBusinessUserSignUp.getProductOfferId());
       //     product.get().addBusinessUser(businessUser);
            product.get().addProductOffer(productOffer.get());
         //   productOffer.get().addProduct(product.get());
            productService.saveProduct(product.get());
            return true;
        }
        return false;
    }
}

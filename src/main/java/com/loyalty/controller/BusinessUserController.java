package com.loyalty.controller;


import com.loyalty.dto.CompleteBusinessUserSignUp;
import com.loyalty.model.BusinessUser;
import com.loyalty.service.BusinessCusomterAdapterService;
import com.loyalty.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BusinessUserController {

    private final BusinessService businessService;
    private final BusinessCusomterAdapterService businessCusomterAdapterService;

    public BusinessUserController(BusinessService businessService, BusinessCusomterAdapterService businessCusomterAdapterService) {
        this.businessService = businessService;
        this.businessCusomterAdapterService = businessCusomterAdapterService;
    }

    @PostMapping("/businessSignup")
    public ResponseEntity<BusinessUser> saveBusinessUser(@RequestBody BusinessUser businessUser){
            BusinessUser businessUser1 = businessService.saveBusinessUser(businessUser);
            return ResponseEntity.ok(businessUser1);
    }

    @GetMapping("/allBusinessUsers")
    public List<BusinessUser> allBusinessUsers(){
        return businessService.findAll();
    }

    @GetMapping("/businessUser")
    public BusinessUser getBusinessUserById(@RequestParam Long id) throws Exception {
        Optional<BusinessUser> businessUser = businessService.findById(id);
        if(businessUser.isPresent()){
            return businessUser.get();
        }else{
           throw  new Exception("Business user does not exists");
        }
    }

    @PostMapping("/completeBusinessUserSignup")
    public BusinessUser completeBusinessUserSignup(@RequestBody CompleteBusinessUserSignUp completeBusinessUserSignUp
                                                   ) {
        return businessCusomterAdapterService.completeBusinessUserSignup(completeBusinessUserSignUp);
    }
}

package dev.danvega.jwt.controller;


import dev.danvega.jwt.dto.CompleteBusinessUserSignUp;
import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.service.BusinessCusomterAdapterService;
import dev.danvega.jwt.service.BusinessService;
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

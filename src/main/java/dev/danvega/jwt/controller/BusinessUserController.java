package dev.danvega.jwt.controller;


import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BusinessUserController {

    private final BusinessService businessService;

    public BusinessUserController(BusinessService businessService) {
        this.businessService = businessService;
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
        return businessService.findById(id).orElseThrow(() -> new Exception("Business user does not exists"));

    }
}

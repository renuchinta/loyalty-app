package dev.danvega.jwt.controller;


import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

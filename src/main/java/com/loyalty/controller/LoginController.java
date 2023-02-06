package com.loyalty.controller;


import com.loyalty.model.LoginRequest;
import com.loyalty.service.BusinessCusomterAdapterService;
import com.loyalty.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final BusinessCusomterAdapterService businessCusomterAdapterService;

    public LoginController(BusinessCusomterAdapterService businessCusomterAdapterService) {
        this.businessCusomterAdapterService = businessCusomterAdapterService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest){
        return businessCusomterAdapterService.login(loginRequest);
    }
}

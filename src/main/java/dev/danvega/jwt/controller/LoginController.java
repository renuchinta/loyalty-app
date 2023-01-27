package dev.danvega.jwt.controller;


import dev.danvega.jwt.model.LoginRequest;
import dev.danvega.jwt.service.BusinessCusomterAdapterService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<HttpStatus> login(@RequestBody LoginRequest loginRequest){
        return businessCusomterAdapterService.login(loginRequest);

    }
}

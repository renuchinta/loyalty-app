// package com.loyalty.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.loyalty.model.LoginRequest;
// import com.loyalty.service.TokenService;

// @RestController
// public class AuthController {

//     private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

//     private final TokenService tokenService;
//     private final AuthenticationManager authenticationManager;

//     public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
//         this.tokenService = tokenService;
//         this.authenticationManager = authenticationManager;
//     }

//     @PostMapping("/token")
//     public String token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
//         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
//         return tokenService.generateToken(authentication);
//     }

// }

package com.loyalty.service;

import com.loyalty.model.BusinessUser;
import com.loyalty.model.LoginRequest;
import com.loyalty.repository.BusinessUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    private final BusinessUserRepository businessUserRepository;

    public BusinessService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;

    }

    public BusinessUser saveBusinessUser(BusinessUser businessUser){
        return businessUserRepository.save(businessUser);
    }

/*

    public BusinessUser findByUserNameAndPassword(LoginRequest loginRequest) {
        return businessUserRepository.findByUserNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
    }
*/

    public Optional<BusinessUser> findById(Long id) {
        return businessUserRepository.findById(id);
    }

    public Optional<BusinessUser> findByBusinessQRId(String businessQRId) {
        return businessUserRepository.findByBusinessQRId(businessQRId);
    }

    public List<BusinessUser> findAll() {
        return businessUserRepository.findAll();
    }


}

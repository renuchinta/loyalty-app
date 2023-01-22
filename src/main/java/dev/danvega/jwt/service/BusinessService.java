package dev.danvega.jwt.service;

import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.repository.BusinessUserRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    
    private final BusinessUserRepository businessUserRepository;

    public BusinessService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    public BusinessUser saveBusinessUser(BusinessUser businessUser){
        return businessUserRepository.save(businessUser);
    }


}

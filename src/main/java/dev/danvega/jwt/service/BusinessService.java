package dev.danvega.jwt.service;

import dev.danvega.jwt.dto.CompleteBusinessUserSignUp;
import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.LoginRequest;
import dev.danvega.jwt.model.Product;
import dev.danvega.jwt.model.ProductOffer;
import dev.danvega.jwt.repository.BusinessUserRepository;
import dev.danvega.jwt.repository.ProductOfferRepository;
import dev.danvega.jwt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    @Autowired
    private ProductService productService;
    private final BusinessUserRepository businessUserRepository;
    private final ProductOfferRepository productOfferRepository;
    private final ProductRepository productRepository;

    public BusinessService(BusinessUserRepository businessUserRepository, ProductOfferRepository productOfferRepository, ProductRepository productRepository) {
        this.businessUserRepository = businessUserRepository;
        this.productOfferRepository = productOfferRepository;
        this.productRepository = productRepository;
    }

    public BusinessUser saveBusinessUser(BusinessUser businessUser){
        return businessUserRepository.save(businessUser);
    }


    public BusinessUser findByUserNameAndPassword(LoginRequest loginRequest) {
        return businessUserRepository.findByUserNameAndPassword(loginRequest.username(),loginRequest.password());
    }

    public Optional<BusinessUser> findById(Long id) {
        return businessUserRepository.findById(id);
    }

    public List<BusinessUser> findAll() {
        return businessUserRepository.findAll();
    }


}

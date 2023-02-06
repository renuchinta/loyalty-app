package com.loyalty.service;

import com.loyalty.model.ProductOffer;
import com.loyalty.repository.ProductOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferService {

    private final ProductOfferRepository productOfferRepository;

    public ProductOfferService(ProductOfferRepository productOfferRepository) {
        this.productOfferRepository = productOfferRepository;
    }

    public ProductOffer saveProductOffers(ProductOffer productOffer) {
        return productOfferRepository.save(productOffer);
    }

    public List<ProductOffer> getAllProductOffers() {
        return productOfferRepository.findAll();
    }
    public Optional<ProductOffer> findById(Long id){
        return productOfferRepository.findById(id);
    }
}

package dev.danvega.jwt.service;

import dev.danvega.jwt.model.ProductOffer;
import dev.danvega.jwt.repository.ProductOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

package com.loyalty.controller;

import com.loyalty.model.ProductOffer;
import com.loyalty.service.ProductOfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductOfferController {

    private final ProductOfferService productOfferService;

    public ProductOfferController(ProductOfferService productOfferService) {
        this.productOfferService = productOfferService;

    }

    /*@PostMapping("/saveProductOffers")
    public ProductOffer saveProductOffers(@RequestBody ProductOffer productOffer) throws Exception {
        Optional<Product> product=  productService.findById(productOffer.getProduct().getId());
        if(product.isPresent()){
            Product productEntity = product.get();
            productEntity.addProductOffer(productOffer);
            ProductOffer productOffer1 = productOfferService.saveProductOffers(productOffer);
            productOffer1.addProduct(productEntity);
            return productOffer1;
        }
        throw new Exception("error while saving product offer ");
    }*/

    @PostMapping("/saveProductOffers")
    public ProductOffer saveProductOffers(@RequestBody ProductOffer productOffer){
            return productOfferService.saveProductOffers(productOffer);
    }
    @GetMapping("/productOffers")
    public List<ProductOffer> productOffers(){
        return productOfferService.getAllProductOffers();
    }


}

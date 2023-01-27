package dev.danvega.jwt.controller;

import dev.danvega.jwt.model.Product;
import dev.danvega.jwt.model.ProductOffer;
import dev.danvega.jwt.service.ProductOfferService;
import dev.danvega.jwt.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductOfferController {

    private final ProductOfferService productOfferService;
    private final ProductService productService;

    public ProductOfferController(ProductOfferService productOfferService, ProductService productService) {
        this.productOfferService = productOfferService;
        this.productService = productService;
    }

    @PostMapping("/saveProductOffers")
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
    }

    @GetMapping("/productOffers")
    public List<ProductOffer> productOffers(){
        return productOfferService.getAllProductOffers();
    }


}

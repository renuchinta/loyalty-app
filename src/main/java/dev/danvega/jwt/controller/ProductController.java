package dev.danvega.jwt.controller;

import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Product;
import dev.danvega.jwt.service.BusinessService;
import dev.danvega.jwt.service.ProductService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductService productService;
    private final BusinessService businessService;


    public ProductController(ProductService productService, BusinessService businessService) {
        this.productService = productService;
        this.businessService = businessService;
    }

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody Product product) throws Exception {
        Optional<BusinessUser> business = businessService.findById(product.getBusinessUser().getId());
        if(business.isPresent()){
            BusinessUser businessUser = business.get();
            businessUser.setProduct(product);
            Product savedProdcut = productService.saveProduct(product);
            savedProdcut.addBusinessUser(businessUser);
            return savedProdcut;
        }
        throw new Exception("Author with id " + product + " does not exist");
    }

    @GetMapping("/products")
    public List<Product> products(){
        return productService.findAllProducts();
    }

    @GetMapping("/getProduct")
    public Product productById(@RequestParam Long id){
        return productService.findById(id).orElseThrow(() -> new RuntimeException("No Product on this id"));
    }
}

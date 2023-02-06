package com.loyalty.controller;

import com.loyalty.model.Product;
import com.loyalty.service.ProductService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

   /* @PostMapping("/saveProduct")
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
    }*/

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody Product product)  {
            return productService.saveProduct(product);
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

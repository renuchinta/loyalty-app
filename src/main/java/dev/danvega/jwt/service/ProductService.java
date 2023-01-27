package dev.danvega.jwt.service;

import dev.danvega.jwt.model.Product;
import dev.danvega.jwt.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
          return productRepository.save(product);

    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
}

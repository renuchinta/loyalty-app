package dev.danvega.jwt.repository;

import dev.danvega.jwt.model.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOfferRepository extends JpaRepository<ProductOffer,Long> {
}

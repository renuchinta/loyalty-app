package dev.danvega.jwt.repository;

import dev.danvega.jwt.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRespository extends JpaRepository<Customer,Long> {
    Customer findByUsernameAndPassword(String username, String password);
}

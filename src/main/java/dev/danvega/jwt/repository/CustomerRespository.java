package dev.danvega.jwt.repository;

import dev.danvega.jwt.dto.BusinessCustomerDTO;
import dev.danvega.jwt.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRespository extends JpaRepository<Customer,Long> {
    Customer findByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true)
    List<BusinessCustomerDTO> getAllBusinessUsers(Long customerId);
}

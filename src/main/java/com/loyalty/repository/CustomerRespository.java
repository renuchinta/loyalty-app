package com.loyalty.repository;

import com.loyalty.dto.BusinessToCustomer;
import com.loyalty.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRespository extends JpaRepository<Customer,Long> {
   // Customer findByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true)
    List<BusinessToCustomer> getAllBusinessUsers(Long customerId);

    Optional<Customer> findByCustomerQRId(String customerQRId);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

}

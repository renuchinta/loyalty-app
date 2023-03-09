package com.loyalty.repository;

import com.loyalty.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {

    CustomerOrder findByCustomerId(Long customerId);


    CustomerOrder findByBusinessIdAndProductIdAndCustomerQrId(Long businessId,Long productId,String customerQRId);
}

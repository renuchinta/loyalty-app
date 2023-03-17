package com.loyalty.service;

import com.loyalty.dto.BusinessCustomDTO;
import com.loyalty.dto.CustomerOrdersTillNow;
import com.loyalty.model.BusinessUser;
import com.loyalty.model.Customer;
import com.loyalty.model.CustomerOrder;
import com.loyalty.model.LoginRequest;
import com.loyalty.repository.BusinessUserRepository;
import com.loyalty.repository.CustomerRespository;
import com.loyalty.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    private final CustomerRespository customerRespository;
    private final BusinessUserRepository businessUserRepository;
    private final OrderRepository orderRepository;



    public CustomerService(CustomerRespository customerRespository, BusinessUserRepository businessUserRepository,
                            OrderRepository orderRepository) {
        this.customerRespository = customerRespository;
        this.businessUserRepository = businessUserRepository;
        this.orderRepository = orderRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRespository.save(customer);

    }
    public Customer findByUserNameAndPassword(LoginRequest loginRequest){
        return customerRespository.findByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
    }

    public Optional<Customer> findById(Long id){
        return customerRespository.findById(id);
    }
    public Boolean getCustomerByQrId(String customerQrId){
        Optional<Customer> customer = customerRespository.findByCustomerQRId(customerQrId);
        if(customer.isPresent()){
            return true;
        }
        return false;
    }
    public List<BusinessCustomDTO> getAllBusinessUsers(Long customerId) {
        return customerRespository.getAllBusinessUsers(customerId)
                .stream()
                .map(a -> businessUserRepository.findById(a.getBusinessID()))
                .map(b -> createBusinessCustomeDTO(b))
                .collect(Collectors.toList());
    }

    public BusinessCustomDTO createBusinessCustomeDTO(Optional<BusinessUser> businessUser1){
        BusinessUser businessUser = businessUser1.get();
        BusinessCustomDTO businessCustomDTO  = new BusinessCustomDTO();
        businessCustomDTO.setBusinessId(businessUser.getId());
        businessCustomDTO.setBusinessName(businessUser.getUserName());
        businessCustomDTO.setBusinessQrId(businessUser.getBusinessQRId());
        businessCustomDTO.setId(businessUser.getId());
        return businessCustomDTO;
    }
    public CustomerOrdersTillNow customerOrdersTillNow(long customerId,long businessId){

        CustomerOrder customerOrder = orderRepository.findByBusinessIdAndCustomerId(businessId,customerId);
        CustomerOrdersTillNow customerOrdersTillNow = new CustomerOrdersTillNow();
        customerOrdersTillNow.setOrderQuantityTillNow(customerOrder.getOrderedQuantity());
        Optional<BusinessUser> businessUser = businessUserRepository.findById(businessId);
        long productQuantity = businessUser.isPresent()
                    ? businessUser.get().getProduct().getProductOffer().getPurchaseQuantity() : 0;
        customerOrdersTillNow.setOfferQuantity(productQuantity);
        return customerOrdersTillNow;
    }


}

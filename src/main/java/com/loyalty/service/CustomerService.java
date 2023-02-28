package com.loyalty.service;

import com.loyalty.model.BusinessUser;
import com.loyalty.model.LoginRequest;
import com.loyalty.repository.BusinessUserRepository;
import com.loyalty.model.Customer;
import com.loyalty.repository.CustomerRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRespository customerRespository;
    private final BusinessUserRepository businessUserRepository;

    public CustomerService(CustomerRespository customerRespository, BusinessUserRepository businessUserRepository) {
        this.customerRespository = customerRespository;
        this.businessUserRepository = businessUserRepository;
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
    public List<BusinessUser> getAllBusinessUsers(Long customerId) {
        return customerRespository.getAllBusinessUsers(customerId).stream()
                                                    .map(a -> businessUserRepository.findById(a.getBusinessID()))
                                                    .flatMap(Optional::stream)
                                                    .collect(Collectors.toList());

    }
}

package dev.danvega.jwt.service;

import dev.danvega.jwt.model.BusinessUser;
import dev.danvega.jwt.model.Customer;
import dev.danvega.jwt.model.LoginRequest;
import dev.danvega.jwt.repository.BusinessUserRepository;
import dev.danvega.jwt.repository.CustomerRespository;
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
        return customerRespository.findByUsernameAndPassword(loginRequest.username(),loginRequest.password());
    }

    public Optional<Customer> findById(Long id){
        return customerRespository.findById(id);
    }

    public List<BusinessUser> getAllBusinessUsers(Long customerId) {
        return customerRespository.getAllBusinessUsers(customerId).stream()
                                                    .map(a -> businessUserRepository.findById(a.getBusinessID()))
                                                    .flatMap(Optional::stream)
                                                    .collect(Collectors.toList());

    }
}

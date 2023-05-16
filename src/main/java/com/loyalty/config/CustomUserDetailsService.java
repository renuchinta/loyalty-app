package com.loyalty.config;


import com.loyalty.dto.CustomUserResponse;
import com.loyalty.dto.UserDTO;
import com.loyalty.model.BusinessUser;
import com.loyalty.model.Customer;
import com.loyalty.model.DAOUser;
import com.loyalty.model.LoginResponse;
import com.loyalty.repository.BusinessUserRepository;
import com.loyalty.repository.CustomerRespository;
import com.loyalty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRespository customerRespository;
	@Autowired
	private BusinessUserRepository businessUserRepository;
	
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;


		/*if(loginRequest.getUserType().equalsIgnoreCase("CUSTOMER")){
			Customer customer=  customerService.findByUserNameAndPassword(loginRequest);
			userDTO.setId(customer.getId());
			userDTO.setEmail(customer.getEmail());
			userDTO.setUsername(customer.getUsername());
			userDTO.setPhoneNumber(customer.getPhoneNumber());
			userDTO.setQrCode(customer.getCustomerQRId());
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		}else{
			BusinessUser businessUser=  businessService.findByUserNameAndPassword(loginRequest);
			userDTO.setId(businessUser.getId());
			userDTO.setPhoneNumber(businessUser.getPhoneNumber());
			userDTO.setQrCode(businessUser.getBusinessQRId() == null ? "" : businessUser.getBusinessQRId());
			return new ResponseEntity<>(userDTO,HttpStatus.OK);
		}
		*/
		// TODO Need to check fields in DAOUser are sufficient or do we need to get from BusinessUser and CusotmerUser class also
		DAOUser user = userDao.findByUsername(username);
		BusinessUser businessUser = businessUserRepository.findByUserId(user.getId());
		LoginResponse loginResponse = new LoginResponse();

		if (user != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			loginResponse.setProductId(businessUser.getProduct().getId());
			loginResponse.setProductName(businessUser.getProduct().getProductName());
			loginResponse.setUsername(user.getUsername());

			return new User(user.getUsername(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}
	
	public CustomUserResponse save(UserDTO user) {
		CustomUserResponse customUserResponse = new CustomUserResponse();
		if(user.getUserType().equalsIgnoreCase("CUSTOMER")){
			Optional<Customer> dbCustomer = customerRespository.findByPhoneNumber(user.getPhoneNumber());
			if(dbCustomer.isPresent()){
				customUserResponse.setCustomMessages("Phone number already exists");
				return customUserResponse;
			}
		}
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		newUser.setUserType(user.getUserType());
		newUser.setEmail(user.getEmail());
		Optional<DAOUser> dbUSer = userDao.findByEmail(user.getEmail());
		if(dbUSer.isPresent()){
			DAOUser daoUser = dbUSer.get();
			if(daoUser.getUserType().equalsIgnoreCase("CUSTOMER")){
				Customer customer = customerRespository.findByPhoneNumber(user.getPhoneNumber()).get();
				setCustomResponseForCustomer(customUserResponse, daoUser, customer);
			}else{
				BusinessUser businessUser = businessUserRepository.findByPhoneNumber(user.getPhoneNumber()).get();
				setCustomResponseForBusiness(customUserResponse, daoUser, businessUser);
			}
			return customUserResponse;
		}else{
			DAOUser savedUser = userDao.save(newUser);
			if(savedUser.getUserType().equalsIgnoreCase("CUSTOMER")){
				setCustomerUser(user, customUserResponse, savedUser);
			}else{
				setBusinessUser(user, customUserResponse, savedUser);
			}
		}
		return customUserResponse;
	}

	private void setCustomerUser(UserDTO user, CustomUserResponse customUserResponse, DAOUser savedUser) {
		Customer customer = new Customer();
		customer.setPhoneNumber(user.getPhoneNumber());
		customer.setUserId(savedUser.getId());
		Customer saveedCustomerUser = customerRespository.save(customer);
		customUserResponse.setEmail(savedUser.getEmail());
		customUserResponse.setPhoneNumber(saveedCustomerUser.getPhoneNumber());
		customUserResponse.setUsername(savedUser.getUsername());
		customUserResponse.setUserType(savedUser.getUserType());
		customUserResponse.setUserType(savedUser.getUserType());
		customUserResponse.setId(savedUser.getId());
	}

	private void setBusinessUser(UserDTO user, CustomUserResponse customUserResponse, DAOUser savedUser) {
		BusinessUser businessUser = new BusinessUser();
		businessUser.setPhoneNumber(user.getPhoneNumber());
		businessUser.setUserId(savedUser.getId());
		BusinessUser saveBusinessUser = businessUserRepository.save(businessUser);
		customUserResponse.setEmail(savedUser.getEmail());
		customUserResponse.setPhoneNumber(saveBusinessUser.getPhoneNumber());
		customUserResponse.setUsername(savedUser.getUsername());
		customUserResponse.setUserType(savedUser.getUserType());
		customUserResponse.setUserType(savedUser.getUserType());
		customUserResponse.setId(savedUser.getId());
	}

	private void setCustomResponseForCustomer(CustomUserResponse customUserResponse, DAOUser daoUser, Customer customer) {
		customUserResponse.setPhoneNumber(customer.getPhoneNumber());
		customUserResponse.setUsername(daoUser.getUsername());
		customUserResponse.setEmail(daoUser.getEmail());
	}

	private void setCustomResponseForBusiness(CustomUserResponse customUserResponse, DAOUser daoUser, BusinessUser businessUser) {
		customUserResponse.setPhoneNumber(businessUser.getPhoneNumber());
		customUserResponse.setEmail(daoUser.getEmail());
		customUserResponse.setUsername(daoUser.getUsername());
		customUserResponse.setUserType(daoUser.getUserType());
		customUserResponse.setId(daoUser.getId());
	}

}

package com.loyalty.service;

import com.loyalty.dto.OrderResponseDTO;
import com.loyalty.model.BusinessUser;
import com.loyalty.model.Customer;
import com.loyalty.model.CustomerOrder;
import com.loyalty.repository.BusinessUserRepository;
import com.loyalty.repository.CustomerRespository;
import com.loyalty.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final OrderRepository orderRepository;
    private final CustomerRespository customerRespository;
    private final BusinessUserRepository businessRepository;

    public OrderService(OrderRepository orderRepository, CustomerRespository customerRespository,BusinessUserRepository businessRepository) {
        this.orderRepository = orderRepository;
        this.customerRespository = customerRespository;
        this.businessRepository = businessRepository;
    }


    public  OrderResponseDTO placeOrder2(@RequestBody CustomerOrder customerOrder) throws Exception {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        CustomerOrder custOrder = orderRepository.findByBusinessIdAndProductIdAndCustomerQrId(customerOrder.getBusinessId()
                                                ,customerOrder.getProductId(),
                                                customerOrder.getCustomerQrId());
        
        
        if(custOrder != null){
            //get qty from db from custOrder;
            Long currentlySavedQty = custOrder.getOrderedQuantity();
            BusinessUser businessUser = businessRepository.findByUserId(customerOrder.getBusinessId());
            if(businessUser != null){
                Long purchaseQuantity = businessUser.getProduct().getProductOffer().getPurchaseQuantity();
                long freeQty = businessUser.getProduct().getProductOffer().getFreeQuantity();
                if(currentlySavedQty+customerOrder.getOrderedQuantity() == purchaseQuantity){
                    orderResponseDTO.setAnyOfferApplied(true);
                    orderResponseDTO.setFreeQuantity(1L);
                    orderResponseDTO.setMessage("User has free quantity");
                    custOrder.setOrderedQuantity(0L);
                    custOrder.setTotalFreeQtyGainedTillNow(custOrder.getTotalFreeQtyGainedTillNow()+1);

                    orderRepository.save(custOrder);
                    return orderResponseDTO;


                }
                if(currentlySavedQty + customerOrder.getOrderedQuantity() < purchaseQuantity) { // 7 < 10
                    custOrder.setOrderedQuantity(currentlySavedQty + customerOrder.getOrderedQuantity());
                    custOrder.setTotalQtyGainedTillNow(currentlySavedQty + customerOrder.getOrderedQuantity());
                    orderRepository.save(custOrder);
                    orderResponseDTO.setAnyOfferApplied(false);
                    orderResponseDTO.setFreeQuantity(0L);
                    orderResponseDTO.setMessage("User has no free quantity");
                }else{
                    // 12 <10 ;  // 10 < 10  -- > 2  2 -1  7+3 =10  , 10 -1  0 - 1 -1
                    long qtyLeftAfterApplyingOffer = (currentlySavedQty + customerOrder.getOrderedQuantity()) - purchaseQuantity;

                    long finalQty = qtyLeftAfterApplyingOffer-freeQty <=  freeQty ? 0 : qtyLeftAfterApplyingOffer-freeQty;
                    custOrder.setOrderedQuantity(finalQty);
                    custOrder.setTotalQtyGainedTillNow(currentlySavedQty + custOrder.getTotalQtyGainedTillNow());
                    custOrder.setTotalFreeQtyGainedTillNow(custOrder.getTotalFreeQtyGainedTillNow()+1);
                    orderRepository.save(custOrder);
                    orderResponseDTO.setAnyOfferApplied(true);
                    orderResponseDTO.setFreeQuantity(freeQty);
                    orderResponseDTO.setMessage("User has free quantity");
                }
            }


        }else{
            CustomerOrder dbCustomerOrder = new CustomerOrder();
            dbCustomerOrder.setBusinessId(customerOrder.getBusinessId());
            dbCustomerOrder.setProductId(customerOrder.getProductId());
            dbCustomerOrder.setOrderedQuantity(customerOrder.getOrderedQuantity());
            dbCustomerOrder.setCustomerQrId(customerOrder.getCustomerQrId());
            Optional<Customer> dbCustomer = customerRespository.findByCustomerQRId(customerOrder.getCustomerQrId());
            dbCustomerOrder.setCustomer(dbCustomer.get());
            dbCustomerOrder.setTotalQtyGainedTillNow(customerOrder.getOrderedQuantity());
            dbCustomerOrder.setTotalFreeQtyGainedTillNow(0L);
            orderRepository.save(dbCustomerOrder);
            orderResponseDTO.setAnyOfferApplied(false);
            orderResponseDTO.setFreeQuantity(0L);
            orderResponseDTO.setMessage("User has no free quantity");
        }                                 
       
        return orderResponseDTO;

    }


}   

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
            Optional<BusinessUser> businessUser = businessRepository.findById(customerOrder.getBusinessId());
            long savedProductOfferQuantity = businessUser.get().getProduct().getProductOffer().getPurchaseQuantity();
            long productOfferSavedFreeQty = businessUser.get().getProduct().getProductOffer().getFreeQuantity();
            if(currentlySavedQty + customerOrder.getOrderedQuantity() < savedProductOfferQuantity) { // 7 < 10
                custOrder.setOrderedQuantity(currentlySavedQty + customerOrder.getOrderedQuantity());
                custOrder.setTotalQtyGainedTillNow(currentlySavedQty + customerOrder.getOrderedQuantity());
                orderRepository.save(custOrder);
            }else{
                // 12 <10 ; 12-10 = 2 in this 2 how many should be free depends on productOfferSavedFreeQty value
                 long totalQtyAfterProductOfferQty = (currentlySavedQty + customerOrder.getOrderedQuantity()) - savedProductOfferQuantity;
               // Now set order qunatity to left out products after substracitng offer quantity
                custOrder.setOrderedQuantity(totalQtyAfterProductOfferQty - productOfferSavedFreeQty);
                custOrder.setTotalQtyGainedTillNow(currentlySavedQty + customerOrder.getOrderedQuantity());
                custOrder.setTotalFreeQtyGainedTillNow(custOrder.getTotalFreeQtyGainedTillNow()+productOfferSavedFreeQty);
                orderRepository.save(custOrder);
                orderResponseDTO.setAnyOfferApplied(true);
                orderResponseDTO.setFreeQuantity(productOfferSavedFreeQty);
                orderResponseDTO.setMessage("Offer is applied ");
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
        }                                 
        orderResponseDTO.setAnyOfferApplied(false);
        orderResponseDTO.setFreeQuantity(0L);
        orderResponseDTO.setMessage("No Offer is applied ");
        return orderResponseDTO;

    }


}   

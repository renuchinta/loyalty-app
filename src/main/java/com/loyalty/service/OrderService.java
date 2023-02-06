package com.loyalty.service;

import com.loyalty.dto.OrderResponseDTO;
import com.loyalty.model.Customer;
import com.loyalty.model.CustomerOrder;
import com.loyalty.repository.CustomerRespository;
import com.loyalty.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final OrderRepository orderRepository;
    private final CustomerRespository customerRespository;

    public OrderService(OrderRepository orderRepository, CustomerRespository customerRespository) {
        this.orderRepository = orderRepository;
        this.customerRespository = customerRespository;
    }


    public  OrderResponseDTO placeOrder(@RequestBody CustomerOrder customerOrder) throws Exception {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        Optional<Customer> customerEntity = customerRespository.findById(customerOrder.getCustomer().getId());
        if(customerEntity.isPresent()){
            Long aggregatedQuantityValue = customerEntity.get().getCustomerOrders().stream().filter(customer -> customer.getStatus().equals(true))
                                            .map(CustomerOrder::getOrderedQuantity).collect(Collectors.summingLong(Long::longValue));
            int productOfferPurchaseQunatity = customerEntity.get().getBusinessUserList().stream().filter(business -> business.getId().equals(customerOrder.getBusinessId()))
                    .findFirst().get().getProduct().getProductOffer().getPurchaseQuantity().intValue();
            int productOfferQunatity = customerEntity.get().getBusinessUserList().stream().filter(business -> business.getId().equals(customerOrder.getBusinessId()))
                    .findFirst().get().getProduct().getProductOffer().getPurchaseQuantity().intValue();
            long finalAccumulatedPurchaseQuantity = aggregatedQuantityValue+customerOrder.getOrderedQuantity();
            if(finalAccumulatedPurchaseQuantity <= productOfferPurchaseQunatity){
                Customer customer = saveActualCustomerOrder(customerOrder.getOrderedQuantity(),customerOrder,customerEntity);
                orderResponseDTO.setFreeQuantity(0L);
                orderResponseDTO.setAnyOfferApplied(false);
                orderResponseDTO.setMessage("Customer has No offer");
                return orderResponseDTO;
            }else{
                long offerQuantity = finalAccumulatedPurchaseQuantity - productOfferPurchaseQunatity;
                long actualQuantityAfterOffer = productOfferQunatity - offerQuantity;
                if(actualQuantityAfterOffer > 0){
                    customerEntity.get().getCustomerOrders().stream().map(order -> {
                        CustomerOrder customerOrder1 = orderRepository.findById(order.getId()).get();
                        customerOrder1.setStatus(false);
                        return null;
                    }).collect(Collectors.toList());
                    Customer customer = saveActualCustomerOrder(customerOrder.getOrderedQuantity(),customerOrder, customerEntity);
                    orderResponseDTO.setFreeQuantity(actualQuantityAfterOffer);
                    orderResponseDTO.setAnyOfferApplied(true);
                    orderResponseDTO.setMessage("Customer has offer");
                    return orderResponseDTO;
                }else{
                   // return  "One coffe should be given free as offer qty and order qty are same";
                    orderResponseDTO.setFreeQuantity(0L);
                    orderResponseDTO.setAnyOfferApplied(true);
                    orderResponseDTO.setMessage("Customer has reached exactly offered quantity");
                    return orderResponseDTO;
                }
            }
        }else{
            throw new Exception("Customer id is not present");
        }
    }

    private Customer saveActualCustomerOrder(Long actualQuantity, CustomerOrder customerOrder, Optional<Customer> customerEntity) {
        customerOrder.setStatus(true);
        customerOrder.setOrderedQuantity(actualQuantity);
        CustomerOrder customerOrderEntity = orderRepository.save(customerOrder);
        customerEntity.get().addCustomerOrders(customerOrderEntity);
        customerOrderEntity.addCustomer(customerEntity.get());
        return customerRespository.save(customerEntity.get());

    }
}

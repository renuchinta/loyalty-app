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


    // public  OrderResponseDTO placeOrder(@RequestBody CustomerOrder customerOrder) throws Exception {
    //     OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    //    // Optional<Customer> customerEntity = customerRespository.findById(customerOrder.getCustomer().getId());
    //    Optional<Customer> customerEntity = customerRespository.findByCustomerQRId(customerOrder.getCustomerQrId());
    //     if(customerEntity.isPresent()){
    //         Long aggregatedQuantityValue = customerEntity.get().getCustomerOrders().stream().filter(customer -> customer.getStatus().equals(true))
    //                                         .map(CustomerOrder::getOrderedQuantity).collect(Collectors.summingLong(Long::longValue));
    //         int productOfferPurchaseQunatity = customerEntity.get().getBusinessUserList().stream().filter(business -> business.getId().equals(customerOrder.getBusinessId()))
    //                 .findFirst().get().getProduct().getProductOffer().getPurchaseQuantity().intValue();
            
    //         long finalAccumulatedPurchaseQuantity = aggregatedQuantityValue+customerOrder.getOrderedQuantity();
    //         if(finalAccumulatedPurchaseQuantity <= productOfferPurchaseQunatity){
    //             saveActualCustomerOrder(customerOrder.getOrderedQuantity(),customerOrder,customerEntity);
    //             orderResponseDTO.setFreeQuantity(0L);
    //             orderResponseDTO.setAnyOfferApplied(false);
    //             orderResponseDTO.setMessage("Customer has No offer");
    //             return orderResponseDTO;
    //         }else{
    //             long offerQuantity = finalAccumulatedPurchaseQuantity - productOfferPurchaseQunatity;
    //             long actualQuantityAfterOffer = productOfferPurchaseQunatity - offerQuantity;
    //             if(actualQuantityAfterOffer > 0){
    //                 customerEntity.get().getCustomerOrders().stream().map(order -> {
    //                     CustomerOrder customerOrder1 = orderRepository.findById(order.getId()).get();
    //                     customerOrder1.setStatus(false);
    //                     return null;
    //                 }).collect(Collectors.toList());
    //                 saveActualCustomerOrder(customerOrder.getOrderedQuantity(),customerOrder, customerEntity);
    //                 orderResponseDTO.setFreeQuantity(actualQuantityAfterOffer);
    //                 orderResponseDTO.setAnyOfferApplied(true);
    //                 orderResponseDTO.setMessage("Customer has offer");
    //                 return orderResponseDTO;
    //             }else{
    //                // return  "One coffe should be given free as offer qty and order qty are same";
    //                 orderResponseDTO.setFreeQuantity(0L);
    //                 orderResponseDTO.setAnyOfferApplied(true);
    //                 orderResponseDTO.setMessage("Customer has reached exactly offered quantity");
    //                 return orderResponseDTO;
    //             }
    //         }
    //     }else{
    //         throw new Exception("Customer id is not present");
    //     }
    // }

    // private CustomerOrder saveActualCustomerOrder(Long actualQuantity, CustomerOrder customerOrder, Optional<Customer> customerEntity) {
        
    //     CustomerOrder cOrder = orderRepository.findByCustomerId(customerEntity.get().getId());
    //     CustomerOrder dbCustomerOrder = cOrder == null  ? new CustomerOrder() : cOrder;
    //     Long qtyt = cOrder == null ? 0 : dbCustomerOrder.getOrderedQuantity();
    //     dbCustomerOrder.setStatus(true);
    //     dbCustomerOrder.setOrderedQuantity(actualQuantity + qtyt);
    //     dbCustomerOrder.setBusinessId(customerOrder.getBusinessId());
    //     dbCustomerOrder.setProductId(customerOrder.getProductId());
    //     return orderRepository.save(dbCustomerOrder);
    //    // customerEntity.get().addCustomerOrders(customerOrderEntity);
    //     //customerOrderEntity.addCustomer(customerEntity.get());
    //     //return customerRespository.save(customerEntity.get());

    // }




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

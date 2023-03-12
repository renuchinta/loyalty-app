package com.loyalty.controller;


import com.loyalty.dto.OrderResponseDTO;
import com.loyalty.model.CustomerOrder;
import com.loyalty.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody CustomerOrder customerOrder) throws Exception {
        OrderResponseDTO orderResponseDTO = orderService.placeOrder2(customerOrder);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    // @GetMapping("/customerOrdersTillNow/{customerId}/{businessId}")
    // public void customerOrdersTillNow(@PathVariable long customerId,@PathVariable long businessId){
    //     CustomerOrdersTillNow customerOrders = orderService.customerOrdersTillNow();

    // }
  
}


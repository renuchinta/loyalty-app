package com.loyalty.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long customerId;
    private Long productId;
    private Long businessId;
    private  Long orderedQuantity;

}

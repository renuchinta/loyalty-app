package com.loyalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private String message;
    @Builder.Default
    private boolean anyOfferApplied = false;
    @Builder.Default
    private Long freeQuantity = 0L;

}

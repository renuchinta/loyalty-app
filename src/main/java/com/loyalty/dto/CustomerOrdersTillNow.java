package com.loyalty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerOrdersTillNow {
    
    private long offerQuantity;
    private long orderQuantityTillNow;

}

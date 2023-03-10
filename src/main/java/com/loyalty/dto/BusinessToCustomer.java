package com.loyalty.dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessToCustomer {
    private Long businessID;
    private Long customerID;
}

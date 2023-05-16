package com.loyalty.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollCustomerToBusiness {

    private Long customerID;
    private String businessQRId;

}

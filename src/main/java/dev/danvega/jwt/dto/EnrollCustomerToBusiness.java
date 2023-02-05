package dev.danvega.jwt.dto;

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

package com.loyalty.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCustomDTO {

    private Long id;
    private Long businessId;
    private String businessQrId;
    private String businessName;
}

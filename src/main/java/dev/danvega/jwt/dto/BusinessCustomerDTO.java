package dev.danvega.jwt.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessCustomerDTO {

    private Long businessID;
    private Long customerID;

}

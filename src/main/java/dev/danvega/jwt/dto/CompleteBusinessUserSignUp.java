package dev.danvega.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteBusinessUserSignUp {
    private Long businessUserId;
    private Long productId;
    private Long productOfferId;
    private String businessQRId;

}

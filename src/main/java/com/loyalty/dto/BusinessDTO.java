package com.loyalty.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BusinessDTO {


    private String address;
    private String phoneNumber;
    private String qrCode;
}

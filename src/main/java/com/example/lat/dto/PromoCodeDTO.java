package com.example.lat.dto;

import com.example.lat.utility.PromoCodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PromoCodeDTO {
    private String code;
    private String expirationDate;
    private Integer maxUsages;
    private Integer discountAmount;
    private String currency;
    private PromoCodeType type;
}

package com.example.lat.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DiscountDTO {
    private BigDecimal regularPrice;
    private BigDecimal discount;
    private BigDecimal discountedPrice;
    private String warning;
}

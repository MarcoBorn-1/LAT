package com.example.lat.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SalesReportDTO {
    private String currency;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private Long purchaseAmount;
}

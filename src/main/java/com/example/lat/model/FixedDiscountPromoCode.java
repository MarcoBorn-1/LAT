package com.example.lat.model;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class FixedDiscountPromoCode extends PromoCode {
    private BigDecimal discountAmount;
    
    public FixedDiscountPromoCode(String code, LocalDate expirationDate, int maxUsages, BigDecimal discountAmount, String currency) {
        super(code, expirationDate, maxUsages, currency);
        if (discountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Discount amount must be positive");
        }
        this.discountAmount = discountAmount;
    }
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        if (!isValid()) {
            throw new IllegalStateException("Promo code is expired or used up");
        }
        return price.subtract(discountAmount);
    }
}

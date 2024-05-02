package com.example.lat.model;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class PercentageDiscountPromoCode extends PromoCode {
    private BigDecimal discountPercentage;

    public PercentageDiscountPromoCode(String code, LocalDate expirationDate, int maxUsages, BigDecimal discountPercentage, String currency) {
        super(code, expirationDate, maxUsages, currency);
        if (discountPercentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Discount percentage must be positive");
        }
        if (discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0 || discountPercentage.compareTo(BigDecimal.valueOf(100)) == 0) {
            throw new IllegalArgumentException("Discount percentage must be less than or equal to 100");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        if (!isValid()) {
            throw new IllegalStateException("Promo code is expired or used up");
        }
        BigDecimal discount = price.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
        return price.subtract(discount);
    }
}

package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class FixedDiscountPromoCode extends PromoCode {
    
    public FixedDiscountPromoCode(String code, LocalDate expirationDate, int maxUsages, BigDecimal discountAmount,
                                  String currency) {
        super(code, expirationDate, maxUsages, currency, PromoCodeType.FIXED);
        setDiscountAmount(discountAmount);
    }

    public FixedDiscountPromoCode() {
    }

    @Override
    public BigDecimal calculateDiscount(Product product) {
        try {
            isPromoCodeValid(product);
            if (product.getPrice().compareTo(getDiscountAmount()) < 0) {
                return product.getPrice().setScale(2, RoundingMode.HALF_UP);
            }
            return getDiscountAmount().setScale(2, RoundingMode.HALF_UP);
        }
        catch (IllegalStateException e) {
            return BigDecimal.ZERO.setScale(2);
        }
    }

    @Override
    public BigDecimal calculateDiscountPrice(Product product) {
        try {
            isPromoCodeValid(product);
            BigDecimal discountPrice = product.getPrice().subtract(getDiscountAmount());
            if (discountPrice.compareTo(BigDecimal.ZERO) < 0) {
                return BigDecimal.ZERO.setScale(2);
            }
            return discountPrice.setScale(2, RoundingMode.HALF_UP);
        }
        catch (IllegalStateException e) {
            return BigDecimal.ZERO.setScale(2);
        }
    }
}

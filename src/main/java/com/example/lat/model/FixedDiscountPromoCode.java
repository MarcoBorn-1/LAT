package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;
import java.math.BigDecimal;
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
        isPromoCodeValid(product);
        BigDecimal discount = product.getPrice().subtract(getDiscountAmount());
        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return discount;
    }
}

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
    public BigDecimal calculateDiscountPrice(Product product) {
        isPromoCodeValid(product);
        BigDecimal discountPrice = product.getPrice().subtract(getDiscountAmount());
        if (discountPrice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return discountPrice;
    }
}

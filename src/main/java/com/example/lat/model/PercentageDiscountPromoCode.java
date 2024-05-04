package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class PercentageDiscountPromoCode extends PromoCode {

    public PercentageDiscountPromoCode(String code, LocalDate expirationDate, int maxUsages,
                                       BigDecimal discountPercentage, String currency) {
        super(code, expirationDate, maxUsages, currency, PromoCodeType.PERCENTAGE);
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount percentage must be equal to or above 0");
        }
        if (discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount percentage must be less than or equal to 100");
        }
        setDiscountAmount(discountPercentage);
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        isPromoCodeValid();
        BigDecimal discount = price.multiply(getDiscountAmount().divide(BigDecimal.valueOf(100)));
        return price.subtract(discount).setScale(2);
    }
}

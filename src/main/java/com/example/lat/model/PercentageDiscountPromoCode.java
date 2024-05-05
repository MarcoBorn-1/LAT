package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class PercentageDiscountPromoCode extends PromoCode {

    public PercentageDiscountPromoCode(String code, LocalDate expirationDate, int maxUsages,
                                       BigDecimal discountPercentage, String currency) {
        super(code, expirationDate, maxUsages, currency, PromoCodeType.PERCENTAGE);
        if (discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount percentage must be less than or equal to 100");
        }
        setDiscountAmount(discountPercentage);
    }

    public PercentageDiscountPromoCode() {
    }

    @Override
    public BigDecimal calculateDiscount(Product product) {
        try {
            isPromoCodeValid(product);
            BigDecimal discount = product.getPrice().multiply(getDiscountAmount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
            return discount.setScale(2, RoundingMode.HALF_UP);
        }
        catch (IllegalStateException e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calculateDiscountPrice(Product product) {
        try {
            isPromoCodeValid(product);
            BigDecimal discount = product.getPrice().multiply(getDiscountAmount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
            return product.getPrice().subtract(discount).setScale(2, RoundingMode.HALF_UP);
        }
        catch (IllegalStateException e) {
            return BigDecimal.ZERO;
        }
    }
}

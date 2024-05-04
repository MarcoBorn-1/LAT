package com.example.lat.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FixedDiscountPromoCodeTest {

    @Test
    void testFixedDiscountConstructor_InvalidDiscountAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(-10), "USD"));
    }

    // 10$ item with 1$ discount - should equal 9$
    @Test
    void testCalculateDiscount_Valid() {
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.ONE, "USD");
        BigDecimal price = BigDecimal.TEN;
        BigDecimal discountedPrice = promoCode.calculateDiscount(price);
        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(9);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 1$ item with 10$ discount - should equal 0$
    @Test
    void testCalculateDiscount_DiscountedPriceBelowZero() {
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.TEN, "USD");
        BigDecimal price = BigDecimal.ONE;
        BigDecimal discountedPrice = promoCode.calculateDiscount(price);
        BigDecimal expectedDiscountedPrice = BigDecimal.ZERO;
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }
}

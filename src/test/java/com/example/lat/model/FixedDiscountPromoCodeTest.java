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
        Product product = new Product("Product", BigDecimal.TEN, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.ONE, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(9);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 1$ item with 10$ discount - should equal 0$
    @Test
    void testCalculateDiscount_DiscountedPriceBelowZero() {
        Product product = new Product("Product", BigDecimal.ONE, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.TEN, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ZERO;
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }
}

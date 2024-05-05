package com.example.lat.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PercentageDiscountPromoCodeTest {

    @Test
    void testConstructor_InvalidDiscountAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(-10), "USD"));
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(105), "USD"));
    }

    // 10$ item with 10% discount - should equal 9$
    @Test
    void testCalculateDiscountPrice_Valid() {
        Product product = new Product("Product", BigDecimal.TEN, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.TEN, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(9).setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    @Test
    void testCalculateDiscountPrice_PriceIsZero() {
        Product product = new Product("Product", BigDecimal.ZERO, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.TEN, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ZERO.setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }
}

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

    // 10$ item with 10% discount - the discount should equal 1$
    @Test
    void testCalculateDiscount_Valid() {
        BigDecimal productPrice = BigDecimal.TEN;
        BigDecimal productDiscountPercent = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscountPercent, "USD");
        BigDecimal discount = promoCode.calculateDiscount(product);
        BigDecimal expectedDiscount = BigDecimal.ONE.setScale(2);
        assertEquals(discount, expectedDiscount);
    }

    // Price of product is 0$, lowering it by 10% - the discount should equal 0$
    @Test
    void testCalculateDiscount_PriceIsZero() {
        BigDecimal productPrice = BigDecimal.ZERO;
        BigDecimal productDiscountPercent = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscountPercent, "USD");
        BigDecimal discount = promoCode.calculateDiscount(product);
        BigDecimal expectedDiscount = BigDecimal.ZERO.setScale(2);
        assertEquals(discount, expectedDiscount);
    }

    // 10$ item with 10% discount - discountedPrice should equal 9$
    @Test
    void testCalculateDiscountPrice_Valid() {
        BigDecimal productPrice = BigDecimal.TEN;
        BigDecimal productDiscountPercent = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscountPercent, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(9).setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 0$ item with 10% discount - discountedPrice should equal 0$
    @Test
    void testCalculateDiscountPrice_PriceIsZero() {
        BigDecimal productPrice = BigDecimal.ZERO;
        BigDecimal productDiscountPercent = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscountPercent, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ZERO.setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }
}

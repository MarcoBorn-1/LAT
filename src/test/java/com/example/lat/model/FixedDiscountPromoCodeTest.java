package com.example.lat.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FixedDiscountPromoCodeTest {

    @Test
    void testConstructor_InvalidDiscountAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(-10), "USD"));
    }

    // 10$ item with 1$ discount - discount should equal 1$
    @Test
    void testCalculateDiscount_Valid() {
        BigDecimal productPrice = BigDecimal.TEN;
        BigDecimal productDiscount = BigDecimal.ONE;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscount, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscount(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ONE.setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 1$ item with 10$ discount - discount should equal price of item (1$)
    @Test
    void testCalculateDiscount_DiscountAmountAboveProductPrice() {
        BigDecimal productPrice = BigDecimal.ONE;
        BigDecimal productDiscount = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscount, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscount(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ONE.setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 10$ item with 1$ discount - discountedPrice should equal 9$
    @Test
    void testCalculateDiscountPrice_Valid() {
        BigDecimal productPrice = BigDecimal.TEN;
        BigDecimal productDiscount = BigDecimal.ONE;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscount, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(9).setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }

    // 1$ item with 10$ discount - discountedPrice should equal 0$
    @Test
    void testCalculateDiscountPrice_DiscountedPriceBelowZero() {
        BigDecimal productPrice = BigDecimal.ONE;
        BigDecimal productDiscount = BigDecimal.TEN;

        Product product = new Product("Product", productPrice, "USD");
        PromoCode promoCode = new FixedDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, productDiscount, "USD");
        BigDecimal discountedPrice = promoCode.calculateDiscountPrice(product);
        BigDecimal expectedDiscountedPrice = BigDecimal.ZERO.setScale(2);
        assertEquals(discountedPrice, expectedDiscountedPrice);
    }
}

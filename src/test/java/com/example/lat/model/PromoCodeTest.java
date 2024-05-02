package com.example.lat.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PromoCodeTest {

    @Test
    void testPromoCodeConstructor_ValidInput() {
        assertDoesNotThrow(() -> new FixedDiscountPromoCode("ABC123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(10), "USD"));
    }

    @Test
    void testPromoCodeConstructor_InvalidCodeLength() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(10), "USD"));
    }

    @Test
    void testPromoCodeConstructor_InvalidCodeCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB@#FG", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(10), "USD"));
    }

    @Test
    void testPromoCodeConstructor_InvalidCodeWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB AA", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(10), "USD"));
    }

    @Test
    void testFixedDiscountConstructor_InvalidDiscountAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(-10), "USD"));
    }

    @Test
    void testPercentageDiscountConstructor_InvalidDiscountAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(-10), "USD"));
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscountPromoCode("AB123", LocalDate.now().plusDays(1), 5, BigDecimal.valueOf(105), "USD"));
    }

    @Test
    void testIsValid_ExpiredCode() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().minusDays(1), 5, BigDecimal.valueOf(10), "USD");
        assertFalse(code.isValid());
    }

    @Test
    void testIsValid_UsageExceeded() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().plusDays(1), 0, BigDecimal.valueOf(10), "USD");
        assertFalse(code.isValid());
    }

    @Test
    void testIsValid_ValidCode() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().plusDays(1), 1, BigDecimal.valueOf(10), "USD");
        assertTrue(code.isValid());
    }
}
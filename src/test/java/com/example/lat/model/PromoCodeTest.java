package com.example.lat.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PromoCodeTest {

    @Test
    void testValidatePromoCodeFormat_ValidInput() {
        assertDoesNotThrow(() -> PromoCode.validatePromoCodeFormat("ABC123"));
    }

    @Test
    void testValidatePromoCodeFormat_InvalidCodeLength() {
        assertThrows(IllegalArgumentException.class, () -> PromoCode.validatePromoCodeFormat("AB"));
    }

    @Test
    void testValidatePromoCodeFormat_InvalidCodeCharacters() {
        assertThrows(IllegalArgumentException.class, () -> PromoCode.validatePromoCodeFormat("AB@#FG"));
    }

    @Test
    void testValidatePromoCodeFormat_InvalidCodeWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> PromoCode.validatePromoCodeFormat("AB AB"));
    }

    @Test
    void testIsPromoCodeValid_ExpiredCode() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().minusDays(1), 5, BigDecimal.valueOf(10), "USD");
        assertThrows(IllegalStateException.class, code::isPromoCodeValid);
    }

    @Test
    void testIsPromoCodeValid_UsageExceeded() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().plusDays(1), 0, BigDecimal.valueOf(10), "USD");
        assertThrows(IllegalStateException.class, code::isPromoCodeValid);
    }

    @Test
    void testIsPromoCodeValid_ValidCode() {
        PromoCode code = new FixedDiscountPromoCode("ABC123", LocalDate.now().plusDays(1), 1, BigDecimal.valueOf(10), "USD");
        assertTrue(code.isPromoCodeValid());
    }
}
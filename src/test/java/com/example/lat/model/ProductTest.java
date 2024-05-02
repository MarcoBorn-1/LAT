package com.example.lat.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {
    @Test
    void testProductConstructor_ValidInput() {
        assertDoesNotThrow(() -> new Product("Product", "A new product", BigDecimal.valueOf(10), "USD"));
    }

    @Test
    void testProductConstructor_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Product", "A new product", BigDecimal.valueOf(-10), "USD"));
    }
}

package com.example.lat.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PurchaseTest {

    @Mock
    private Product mockProduct;

    @Mock
    private PromoCode mockPromoCode;

    public PurchaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchase_WithValidPromoCode() {
        LocalDate purchaseDate = LocalDate.of(2024, 5, 1);
        BigDecimal productPrice = BigDecimal.valueOf(100);
        BigDecimal discountAmount = BigDecimal.valueOf(20);

        when(mockProduct.getPrice()).thenReturn(productPrice);
        when(mockPromoCode.calculateDiscount(mockProduct)).thenReturn(discountAmount);

        Purchase purchase = new Purchase(purchaseDate, mockProduct, mockPromoCode);

        assertEquals(purchaseDate, purchase.getPurchaseDate());
        assertEquals(productPrice, purchase.getRegularPrice());
        assertEquals(discountAmount, purchase.getDiscountAmount());
        assertEquals(mockProduct, purchase.getProduct());
    }

    @Test
    void testPurchase_WithInvalidPromoCode() {
        LocalDate purchaseDate = LocalDate.of(2024, 5, 1);
        BigDecimal productPrice = BigDecimal.valueOf(100);

        when(mockProduct.getPrice()).thenReturn(productPrice);
        when(mockPromoCode.calculateDiscount(mockProduct)).thenThrow(new IllegalStateException("Promo code is expired"));

        assertThrows(IllegalStateException.class, () -> new Purchase(purchaseDate, mockProduct, mockPromoCode));
    }

    @Test
    void testPurchase_WithoutPromoCode() {
        LocalDate purchaseDate = LocalDate.of(2024, 5, 1);
        BigDecimal productPrice = BigDecimal.valueOf(100);

        when(mockProduct.getPrice()).thenReturn(productPrice);

        Purchase purchase = new Purchase(purchaseDate, mockProduct);

        assertEquals(purchaseDate, purchase.getPurchaseDate());
        assertEquals(productPrice, purchase.getRegularPrice());
        assertEquals(BigDecimal.ZERO, purchase.getDiscountAmount());
        assertEquals(mockProduct, purchase.getProduct());
    }
}
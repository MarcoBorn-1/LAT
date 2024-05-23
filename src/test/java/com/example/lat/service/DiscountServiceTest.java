package com.example.lat.service;

import com.example.lat.dto.DiscountDTO;
import com.example.lat.model.PercentageDiscountPromoCode;
import com.example.lat.model.Product;
import com.example.lat.model.PromoCode;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DiscountServiceTest {
    @Mock
    private ProductService productService;
    @Mock
    private PromoCodeService promoCodeService;

    @InjectMocks
    private DiscountService discountService;

    @Test
    public void testCalculateDiscountPrice_ValidProductAndPromoCode() {
        // Mock product
        Product product = new Product("Product", "A new product", BigDecimal.valueOf(10), "USD");
        product.setPrice(BigDecimal.valueOf(100));

        // Mock promo code
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().plusDays(1), 10, BigDecimal.TEN, "USD");


        // Mock repository methods
        when(productService.getProductById(1L)).thenReturn(product);
        when(promoCodeService.getPromoCodeByCode("CODE")).thenReturn(promoCode);

        // Test method
        DiscountDTO discountDTO = discountService.calculateProductDiscountWithCode(1L, "CODE");

        // Verify
        assertEquals(BigDecimal.valueOf(90).setScale(2), discountDTO.getDiscountedPrice());
        assertEquals(BigDecimal.TEN.setScale(2), discountDTO.getDiscount());
        assertEquals(BigDecimal.valueOf(100).setScale(2), discountDTO.getRegularPrice());
        assertNull(discountDTO.getWarning());
    }

    @Test
    public void testCalculateDiscountPrice_ProductNotFound() {
        // Mock repository method to throw exception
        when(productService.getProductById(1L)).thenThrow(NoSuchElementException.class);

        // Test method and assert exception
        assertThrows(NoSuchElementException.class, () -> discountService.calculateProductDiscountWithCode(1L, "CODE123"));
    }

    @Test
    public void testCalculateDiscountPrice_PromoCodeNotFound() {
        // Mock product
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));

        // Mock repository methods
        when(productService.getProductById(1L)).thenReturn(product);
        when(promoCodeService.getPromoCodeByCode("INVALID_CODE")).thenThrow(NoSuchElementException.class);

        // Test method and assert exception
        assertThrows(NoSuchElementException.class, () -> discountService.calculateProductDiscountWithCode(1L, "INVALID_CODE"));
    }

    @Test
    public void testCalculateDiscountPrice_ExpiredPromoCode() {
        // Mock product
        Product product = new Product("Product", "A new product", BigDecimal.valueOf(10), "USD");
        product.setPrice(BigDecimal.valueOf(100));

        // Mock promo code
        PromoCode promoCode = new PercentageDiscountPromoCode("CODE", LocalDate.now().minusDays(1), 10, BigDecimal.TEN, "USD");

        // Mock repository methods
        when(productService.getProductById(1L)).thenReturn(product);
        when(promoCodeService.getPromoCodeByCode("CODE")).thenReturn(promoCode);

        // Test method
        DiscountDTO discountDTO = discountService.calculateProductDiscountWithCode(1L, "CODE");

        // Verify
        assertEquals(BigDecimal.valueOf(100).setScale(2), discountDTO.getDiscountedPrice());
        assertEquals(BigDecimal.ZERO.setScale(2), discountDTO.getDiscount());
        assertEquals(BigDecimal.valueOf(100).setScale(2), discountDTO.getRegularPrice());
        assertNotNull(discountDTO.getWarning());
    }
}
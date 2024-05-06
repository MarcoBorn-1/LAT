package com.example.lat.service;

import com.example.lat.dto.PromoCodeDTO;
import com.example.lat.model.FixedDiscountPromoCode;
import com.example.lat.model.PromoCode;
import com.example.lat.repository.PromoCodeRepository;
import com.example.lat.utility.PromoCodeType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PromoCodeServiceTest {

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @InjectMocks
    private PromoCodeService promoCodeService;

    @Test
    public void testCreatePromoCode_Success() {
        // Mock input DTO
        PromoCodeDTO promoCodeDTO = new PromoCodeDTO("CODE123", "01.01.2025", 10, 50, "USD", PromoCodeType.FIXED);

        // Mock repository save method
        when(promoCodeRepository.save(Mockito.any(PromoCode.class))).thenReturn(new FixedDiscountPromoCode());

        // Test method
        PromoCode promoCode = promoCodeService.createPromoCode(promoCodeDTO);

        // Verify
        assertNotNull(promoCode);
    }

    @Test
    public void testGetAllPromoCodes_Success() {
        // Mock promo code data
        PromoCode promoCode1 = new FixedDiscountPromoCode("CODE123", LocalDate.now(), 10, BigDecimal.valueOf(50.00), "USD");
        PromoCode promoCode2 = new FixedDiscountPromoCode("CODE456", LocalDate.now(), 20, BigDecimal.valueOf(20.00), "EUR");
        List<PromoCode> promoCodeList = new ArrayList<>();
        promoCodeList.add(promoCode1);
        promoCodeList.add(promoCode2);

        // Mock repository findAll method
        when(promoCodeRepository.findAll()).thenReturn(promoCodeList);

        // Test method
        List<PromoCode> result = promoCodeService.getAllPromoCodes();

        // Verify
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPromoCodeByCode_Success() {
        // Mock promo code
        PromoCode promoCode = new FixedDiscountPromoCode("CODE123", LocalDate.now(), 10, new BigDecimal("50.00"), "USD");

        // Mock repository findByCode method
        when(promoCodeRepository.findByCode("CODE123")).thenReturn(Optional.of(promoCode));

        // Test method
        PromoCode result = promoCodeService.getPromoCodeByCode("CODE123");

        // Verify
        assertNotNull(result);
    }

    @Test
    public void testGetPromoCodeByCode_NotFound() {
        // Mock repository findByCode method to return empty optional
        when(promoCodeRepository.findByCode("INVALID_CODE")).thenReturn(Optional.empty());

        // Test method and assert exception
        assertThrows(NoSuchElementException.class, () -> promoCodeService.getPromoCodeByCode("INVALID_CODE"));
    }
}
package com.example.lat.service;

import com.example.lat.dto.DiscountDTO;
import com.example.lat.model.Product;
import com.example.lat.model.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {
    private final ProductService productService;
    private final PromoCodeService promoCodeService;

    @Autowired
    public DiscountService(ProductService productService, PromoCodeService promoCodeService) {
        this.productService = productService;
        this.promoCodeService = promoCodeService;
    }

    public DiscountDTO calculateProductDiscountWithCode(Long productId, String code) {
        DiscountDTO discountDTO = new DiscountDTO();
        // Get product by ID and check, if it exists (throws NoSuchElementException, if doesn't exist)
        Product product = productService.getProductById(productId);

        // Get promo code by code and check, if it exists (throws NoSuchElementException, if doesn't exist)
        PromoCode promoCode = promoCodeService.getPromoCodeByCode(code);

        // Check, if promo code is valid
        try {
            discountDTO.setDiscountedPrice(promoCode.calculateDiscountPrice(product));
            discountDTO.setDiscount(promoCode.calculateDiscount(product));
            discountDTO.setRegularPrice(product.getPrice());
            discountDTO.setWarning(null);
            return discountDTO;
        }
        catch (IllegalStateException e) {
            // If promo code was invalid, catch the exception message, insert into DTO and return regular price
            discountDTO.setWarning(e.getMessage());
            discountDTO.setDiscount(BigDecimal.ZERO);
            discountDTO.setRegularPrice(product.getPrice());
            discountDTO.setDiscountedPrice(product.getPrice());
            return discountDTO;
        }
    }
}

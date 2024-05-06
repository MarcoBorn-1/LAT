package com.example.lat.controller;

import com.example.lat.dto.DiscountDTO;
import com.example.lat.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<DiscountDTO> calculateDiscount(@RequestParam Long productId, @RequestParam String promoCode) {
        DiscountDTO discountDTO = discountService.calculateProductDiscountWithCode(productId, promoCode);
        return ResponseEntity.status(201).body(discountDTO);
    }
}

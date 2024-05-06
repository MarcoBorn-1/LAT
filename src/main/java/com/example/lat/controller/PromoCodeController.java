package com.example.lat.controller;

import com.example.lat.dto.PromoCodeDTO;
import com.example.lat.model.PromoCode;
import com.example.lat.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promo-code")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @Autowired
    PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/create")
    public ResponseEntity<PromoCode> createPromoCode(@RequestBody PromoCodeDTO promoCodeDTO) {
        PromoCode promoCode = promoCodeService.createPromoCode(promoCodeDTO);
        return ResponseEntity.status(201).body(promoCode);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PromoCode>> getAllPromoCodes() {
        List<PromoCode> promoCodeList = promoCodeService.getAllPromoCodes();
        return ResponseEntity.status(200).body(promoCodeList);
    }

    @GetMapping("/{code}")
    public ResponseEntity<PromoCode> getPromoCodeByCode(@PathVariable String code) {
        PromoCode promoCode = promoCodeService.getPromoCodeByCode(code);
        return ResponseEntity.status(200).body(promoCode);
    }
}

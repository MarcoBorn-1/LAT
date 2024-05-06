package com.example.lat.controller;

import com.example.lat.dto.SalesReportDTO;
import com.example.lat.model.Purchase;
import com.example.lat.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Purchase> buyProduct(@RequestParam Long productId, @RequestParam(required = false) String promoCode) {
        Purchase purchase = purchaseService.buyItem(productId, promoCode);
        return ResponseEntity.status(200).body(purchase);
    }

    @GetMapping("/report")
    public ResponseEntity<List<SalesReportDTO>> createSalesReport() {
        List<SalesReportDTO> salesReportDTOList = purchaseService.createSalesReport();
        return ResponseEntity.status(200).body(salesReportDTOList);
    }
}

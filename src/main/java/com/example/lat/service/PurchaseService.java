package com.example.lat.service;

import com.example.lat.dto.SalesReportDTO;
import com.example.lat.model.Product;
import com.example.lat.model.PromoCode;
import com.example.lat.model.Purchase;
import com.example.lat.repository.PromoCodeRepository;
import com.example.lat.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    private final ProductService productService;
    private final PromoCodeService promoCodeService;
    private final PurchaseRepository purchaseRepository;
    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public PurchaseService(ProductService productService,
                           PromoCodeService promoCodeService,
                           PurchaseRepository purchaseRepository,
                           PromoCodeRepository promoCodeRepository) {
        this.productService = productService;
        this.promoCodeService = promoCodeService;
        this.purchaseRepository = purchaseRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    public Purchase buyItem(Long itemId, String code) {
        // Get product by ID and check, if it exists
        Product product = productService.getProductById(itemId);

        if (code == null) {
            // No promo code used, creating a new Purchase object
            Purchase purchase = new Purchase(
                    LocalDate.now(),
                    product
            );

            // Saving purchase to the database
            purchaseRepository.save(purchase);
            return purchase;
        }

        // Get promo code by code and check, if it exists
        PromoCode promoCode = promoCodeService.getPromoCodeByCode(code);

        // Create new Purchase object with promoCode
        Purchase purchase = new Purchase(
                LocalDate.now(),
                product,
                promoCode
        );

        // Save into database
        purchaseRepository.save(purchase);

        // Add one code usage into promo code and update database
        promoCode.incrementUsages();
        promoCodeRepository.save(promoCode);

        return purchase;
    }

    public List<SalesReportDTO> createSalesReport() {
        // Create list with data in arrays
        List<Object[]> salesReportDataArray = purchaseRepository.getSalesReportData();

        // Result list with all the data sorted
        ArrayList<SalesReportDTO> salesReportDTOArrayList = new ArrayList<>();

        // Shift through the data and save it into a list of SalesReportDTOs
        // Data is sorted according to the query in PurchaseRepository:
        // 0: currency | 1: sum of regular price | 2: sum of discount amount | 3: total number of transactions
        // Grouped by currency
        for (Object[] objArr: salesReportDataArray) {
            SalesReportDTO salesReportDTO = new SalesReportDTO();
            salesReportDTO.setCurrency((String) objArr[0]);
            salesReportDTO.setTotalAmount((BigDecimal) objArr[1]);
            salesReportDTO.setTotalDiscount((BigDecimal) objArr[2]);
            salesReportDTO.setPurchaseAmount((Long) objArr[3]);
            salesReportDTOArrayList.add(salesReportDTO);
        }
        return salesReportDTOArrayList;
    }
}

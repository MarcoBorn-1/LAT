package com.example.lat.repository;

import com.example.lat.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("SELECT p.product.currency, SUM(p.regularPrice), SUM(p.discountAmount), COUNT(p) " +
            "FROM Purchase p " +
            "GROUP BY p.product.currency")
    List<Object[]> getSalesReportData();
}

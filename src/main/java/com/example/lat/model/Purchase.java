package com.example.lat.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Getter
public class Purchase {
    public Purchase(LocalDate purchaseDate, Product product, PromoCode code) throws IllegalStateException {
        // If promo code is invalid, throws IllegalStateException with details inside the message
        code.isPromoCodeValid(product);
        this.purchaseDate = purchaseDate;
        this.regularPrice = product.getPrice().setScale(2, RoundingMode.HALF_UP);
        this.discountAmount = code.calculateDiscount(product);
        this.product = product;
    }

    public Purchase(LocalDate purchaseDate, Product product) {
        this.purchaseDate = purchaseDate;
        this.regularPrice = product.getPrice();
        this.discountAmount = BigDecimal.ZERO.setScale(2);
        this.product = product;
    }

    public Purchase() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "regular_price", nullable = false)
    private BigDecimal regularPrice;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
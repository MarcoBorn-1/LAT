package com.example.lat.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
public class Product {
    public Product(String name, String description, BigDecimal price, String currency) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        this.name = name;
        this.description = description;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public Product(String name, BigDecimal price, String currency) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        this.name = name;
        this.description = null;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String currency;
}

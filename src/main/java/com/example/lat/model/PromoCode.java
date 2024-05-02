package com.example.lat.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class PromoCode {
    public PromoCode(String code, LocalDate expirationDate, int maxUsages, String currency) {
        if (code.length() < 3 || code.length() > 24) {
            throw new IllegalArgumentException("Entered promo code is too short or too long");
        }
        else if (!Pattern.matches("[A-Z0-9]+", code)) {
            throw new IllegalArgumentException("Invalid promo code format");
        }
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
        this.currentUsages = 0;
        this.currency = currency;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private int maxUsages;

    @Column(nullable = false)
    private int currentUsages;

    @Column(nullable = false)
    private String currency;

    public boolean isValid() {
        return LocalDate.now().isBefore(expirationDate) && (currentUsages < maxUsages);
    }

    public abstract BigDecimal calculateDiscount(BigDecimal price);
}

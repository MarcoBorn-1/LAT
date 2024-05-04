package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Entity
public abstract class PromoCode {
    protected PromoCode(String code, LocalDate expirationDate, int maxUsages,
                     String currency, PromoCodeType type) {
        validatePromoCodeFormat(code);
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
        this.currentUsages = 0;
        this.currency = currency;
        this.type = type;
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
    private BigDecimal discountAmount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private PromoCodeType type;

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static void validatePromoCodeFormat(String code) {
        if (code.length() < 3 || code.length() > 24) {
            throw new IllegalArgumentException("Entered promo code is too short or too long");
        }
        else if (!Pattern.matches("[A-Z0-9]+", code)) {
            throw new IllegalArgumentException("Invalid promo code format");
        }
    }

    public boolean isPromoCodeValid() {
        if (!LocalDate.now().isBefore(expirationDate)) {
            throw new IllegalStateException("Promo code is expired");
        }
        else if (currentUsages >= maxUsages) {
            throw new IllegalStateException("Promo code is used up");
        }

        return true;
    }

    public abstract BigDecimal calculateDiscount(BigDecimal price);
}

package com.example.lat.model;

import com.example.lat.utility.PromoCodeType;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Entity
@Getter
public abstract class PromoCode {
    protected PromoCode(String code, LocalDate expirationDate, int maxUsages,
                     String currency, PromoCodeType type) {
        validatePromoCodeFormat(code);
        validateMaxUsages(maxUsages);
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
        this.currentUsages = 0;
        this.currency = currency;
        this.type = type;
    }

    public PromoCode() {
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

    public void setDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount amount must be equal to or above 0");
        }
        this.discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public static void validateMaxUsages(int maxUsages) {
        if (maxUsages < 0) {
            throw new IllegalArgumentException("Maximum usages must be equal to or above 0");
        }
    }

    public static void validatePromoCodeFormat(String code) {
        if (code.length() < 3 || code.length() > 24) {
            throw new IllegalArgumentException("Entered promo code is too short or too long");
        }
        else if (!Pattern.matches("[A-Z0-9]+", code)) {
            throw new IllegalArgumentException("Invalid promo code format");
        }
    }

    public boolean isPromoCodeValid(Product product) {
        if (!LocalDate.now().isBefore(expirationDate)) {
            throw new IllegalStateException("Promo code is expired");
        }
        else if (currentUsages >= maxUsages) {
            throw new IllegalStateException("Promo code is used up");
        }
        else if (!product.getCurrency().equals(currency)) {
            throw new IllegalStateException("The currency of the promo code " + code + " is not supported by the product's currency.");
        }

        return true;
    }

    public abstract BigDecimal calculateDiscountPrice(Product product);

    public abstract BigDecimal calculateDiscount(Product product);
}

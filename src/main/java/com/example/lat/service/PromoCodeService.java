package com.example.lat.service;

import com.example.lat.dto.PromoCodeDTO;
import com.example.lat.exception.MissingValueException;
import com.example.lat.model.FixedDiscountPromoCode;
import com.example.lat.model.PercentageDiscountPromoCode;
import com.example.lat.model.PromoCode;
import com.example.lat.repository.PromoCodeRepository;
import com.example.lat.utility.PromoCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    public PromoCode createPromoCode(PromoCodeDTO promoCodeDTO) {
        // Check, if all fields have been initialised
        if (promoCodeDTO.getCode() == null || promoCodeDTO.getType() == null ||
                promoCodeDTO.getDiscountAmount() == null || promoCodeDTO.getMaxUsages() == null ||
                promoCodeDTO.getExpirationDate() == null || promoCodeDTO.getCurrency() == null) {
            StringBuilder errorMessage = new StringBuilder("Missing required fields - ");
            List<String> missingFields = new ArrayList<>();
            if (promoCodeDTO.getCode() == null) {
                missingFields.add("code");
            }
            if (promoCodeDTO.getType() == null) {
                missingFields.add("type");
            }
            if (promoCodeDTO.getDiscountAmount() == null) {
                missingFields.add("discountAmount");
            }
            if (promoCodeDTO.getMaxUsages() == null) {
                missingFields.add("maxUsages");
            }
            if (promoCodeDTO.getExpirationDate() == null) {
                missingFields.add("expirationDate");
            }
            if (promoCodeDTO.getCurrency() == null) {
                missingFields.add("currency");
            }
            errorMessage.append(String.join(", ", missingFields));
            throw new MissingValueException(errorMessage.toString());
        }

        // Convert the date from String into LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate expirationDate = LocalDate.parse(promoCodeDTO.getExpirationDate(), formatter);

        if (promoCodeDTO.getType() == PromoCodeType.FIXED) {
            // If type is FIXED, creates a PromoCode for a fixed amount
            FixedDiscountPromoCode promoCode = new FixedDiscountPromoCode(
                    promoCodeDTO.getCode(),
                    expirationDate,
                    promoCodeDTO.getMaxUsages(),
                    new BigDecimal(promoCodeDTO.getDiscountAmount()),
                    promoCodeDTO.getCurrency()
            );
            promoCodeRepository.save(promoCode);
            return promoCode;
        }
        else {
            // Else if type is PERCENTAGE, creates a PromoCode for a percentage-based discount
            PercentageDiscountPromoCode promoCode = new PercentageDiscountPromoCode(
                    promoCodeDTO.getCode(),
                    expirationDate,
                    promoCodeDTO.getMaxUsages(),
                    new BigDecimal(promoCodeDTO.getDiscountAmount()),
                    promoCodeDTO.getCurrency()
            );
            promoCodeRepository.save(promoCode);
            return promoCode;
        }
    }

    public List<PromoCode> getAllPromoCodes() {
        return promoCodeRepository.findAll();
    }

    public PromoCode getPromoCodeByCode(String code) {
        Optional<PromoCode> optionalPromoCode = promoCodeRepository.findByCode(code);
        if (optionalPromoCode.isEmpty()) {
            throw new NoSuchElementException("Promo code not found with code: " + code);
        }
        return optionalPromoCode.get();
    }
}

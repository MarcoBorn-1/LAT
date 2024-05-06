package com.example.lat.service;

import com.example.lat.dto.ProductDTO;
import com.example.lat.exception.MissingValueException;
import com.example.lat.model.Product;
import com.example.lat.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDTO productDTO){
        if (productDTO.getName() == null || productDTO.getPrice() == null || productDTO.getCurrency() == null) {
            StringBuilder errorMessage = new StringBuilder("Missing required fields - ");
            List<String> missingFields = new ArrayList<>();
            if (productDTO.getName() == null) {
                missingFields.add("name");
            }
            if (productDTO.getPrice() == null) {
                missingFields.add("price");
            }
            if (productDTO.getCurrency() == null) {
                missingFields.add("currency");
            }
            errorMessage.append(String.join(", ", missingFields));
            throw new MissingValueException(errorMessage.toString());
        }
        Product product = new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getCurrency());
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (productDTO.getName() != null) {
                product.setName(productDTO.getName());
            }
            if (productDTO.getDescription() != null) {
                product.setDescription(productDTO.getDescription());
            }
            if (productDTO.getPrice() != null) {
                product.setPrice(productDTO.getPrice());
            }
            if (productDTO.getCurrency() != null) {
                product.setCurrency(productDTO.getCurrency());
            }
            return product;
        }
        else {
            throw new NoSuchElementException("No product with given ID exists");
        }
    }
}

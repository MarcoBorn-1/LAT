package com.example.lat.service;

import com.example.lat.dto.ProductDTO;
import com.example.lat.exception.MissingValueException;
import com.example.lat.model.Product;
import com.example.lat.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    //
    // CreateProduct tests
    //

    @Test
    public void testCreateProduct_Success() {
        // Mock input DTO
        ProductDTO productDTO = new ProductDTO("Test Product", "Description", BigDecimal.valueOf(11.99), "USD");

        // Mock repository save method
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());

        // Test method
        Product product = productService.createProduct(productDTO);

        // Verify
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testCreateProduct_Failure() {
        // Mock input DTO - no information about the price
        ProductDTO productDTO = new ProductDTO("Product", "Description", null, "USD");

        // Test method
        assertThrows(MissingValueException.class, () -> productService.createProduct(productDTO));
    }

    //
    // GetAllProducts tests
    //

    // Products present in database
    @Test
    public void testGetAllProducts() {
        // Mock product data
        Product product1 = new Product("Product 1", "Description 1", BigDecimal.valueOf(10.10), "USD");
        Product product2 = new Product("Product 2", "Description 2", BigDecimal.valueOf(20.20), "USD");
        List<Product> productList = Arrays.asList(product1, product2);

        // Mock repository findAll method
        when(productRepository.findAll()).thenReturn(productList);

        // Test method
        List<Product> result = productService.getAllProducts();

        // Verify
        assertEquals(2, result.size());
    }

    //
    // UpdateProduct tests
    //

    // Successful change of product information
    @Test
    public void testUpdateProduct_Success() {
        // Mock input DTO
        ProductDTO productDTO = new ProductDTO("Updated Product", "Updated Description", BigDecimal.valueOf(15.99), "EUR");

        // Mock product
        Product product = new Product("Original Product", "Original Description", BigDecimal.valueOf(10.00), "USD");

        // Mock repository findById method
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Test method
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Verify
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(15.99), updatedProduct.getPrice());
        assertEquals("EUR", updatedProduct.getCurrency());
    }

    // Trying to update a product ID not present in database
    @Test
    public void testUpdateProduct_ProductNotFound() {
        // Mock input DTO
        ProductDTO productDTO = new ProductDTO("Updated Product", "Updated Description", BigDecimal.valueOf(15.99), "EUR");

        // Mock repository findById method to return empty optional
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Test method and assert exception
        assertThrows(NoSuchElementException.class, () -> productService.updateProduct(1L, productDTO));
    }

    // Updating a product without every single value - here it's missing an updated name
    @Test
    public void testUpdateProduct_ProductDtoHasNullValues() {
        // Mock input DTO - has no name
        ProductDTO productDTO = new ProductDTO(null, "Updated Description", BigDecimal.valueOf(15.99), "EUR");

        // Mock product
        Product product = new Product("Original Product", "Original Description", BigDecimal.valueOf(10.00), "USD");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Test method
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Verify
        assertEquals("Original Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(15.99), updatedProduct.getPrice());
        assertEquals("EUR", updatedProduct.getCurrency());
    }

    // Trying to update product with negative price
    @Test
    public void testUpdateProduct_ProductDtoHasNegativePrice() {
        // Mock input DTO - has no name
        ProductDTO productDTO = new ProductDTO(null, "Updated Description", BigDecimal.valueOf(-5), "EUR");

        // Mock product
        Product product = new Product("Original Product", "Original Description", BigDecimal.valueOf(10.00), "USD");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Test method
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(1L, productDTO));
    }
}
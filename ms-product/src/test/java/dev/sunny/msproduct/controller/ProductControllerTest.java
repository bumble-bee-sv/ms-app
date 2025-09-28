package dev.sunny.msproduct.controller;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.exceptions.product.ProductNotFoundException;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Random;
import java.util.random.RandomGenerator;

@SpringBootTest
class ProductControllerTest {

    MockMvc mockMvc;

    @Autowired
    ProductController productController;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Rollback
    @Transactional
    void addProduct() {

        ProductDto productDto = ProductDto.builder()
                .title("Test Product")
                .price(new BigDecimal(99))
                .description("This is a test product")
                .category("Test Category")
                .image("https://example.com/image.jpg")
                .build();

        ProductDto savedProduct = productController.addProduct(productDto);

        Assertions.assertNotNull(savedProduct, "Saved product should not be null");
        Assertions.assertNotNull(savedProduct.getUniqueId(), "Saved product ID should not be null");

        Product productEntity = productRepository.findById(savedProduct.getUniqueId()).orElse(null);
        Assertions.assertNotNull(productEntity, "Product entity should not be null");
        Assertions.assertNotNull(productEntity.getCategory(), "Product category should not be null");

    }

    @Test
    @Transactional
    void getProductById() throws ProductNotFoundException {

        ProductDto rawProductDto = ProductDto.builder()
                .title("Test Product")
                .price(new BigDecimal(99))
                .description("This is a test product")
                .category("Test Category")
                .image("https://example.com/image.jpg")
                .build();
        Product savedProductEntity = productRepository.save(productMapper.toEntity(rawProductDto));
        Assertions.assertNotNull(savedProductEntity, "Saved product should not be null");

        Product product = productRepository.findById(1L).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + 1L));
        ProductDto productDto = productController.getProductById(1L);

        Assertions.assertEquals(product.getId(), productDto.getUniqueId(), "Product ID should be equal");
        Assertions.assertEquals(product.getTitle(), productDto.getTitle(), "Product title should be equal");

        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productController.getProductById(Random.from(RandomGenerator.getDefault()).nextLong(100, 1000)),
                "Should throw exception for non-existing product");

    }
}
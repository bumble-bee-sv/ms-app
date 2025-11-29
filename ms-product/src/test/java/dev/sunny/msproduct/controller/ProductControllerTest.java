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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    @Transactional
    void getAllProducts() {
        ProductDto productDto1 = ProductDto.builder()
                .title("Product 1")
                .price(new BigDecimal(10))
                .description("Desc 1")
                .category("Cat 1")
                .image("img1.jpg")
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .title("Product 2")
                .price(new BigDecimal(20))
                .description("Desc 2")
                .category("Cat 2")
                .image("img2.jpg")
                .build();
        productController.addProduct(productDto1);
        productController.addProduct(productDto2);
        Assertions.assertFalse(productController.getAllProducts(false).isEmpty(), "Product list should not be empty");
    }

    @Test
    @Transactional
    void updateProduct() {
        ProductDto productDto = ProductDto.builder()
                .title("Original Title")
                .price(new BigDecimal(50))
                .description("Original Desc")
                .category("Original Cat")
                .image("original.jpg")
                .build();
        ProductDto saved = productController.addProduct(productDto);
        ProductDto updateDto = ProductDto.builder()
                .title("Updated Title")
                .price(new BigDecimal(60))
                .description("Updated Desc")
                .category("Updated Cat")
                .image("updated.jpg")
                .build();
        ProductDto updated = productController.updateProduct(saved.getUniqueId(), updateDto);
        Assertions.assertEquals("Updated Title", updated.getTitle(), "Title should be updated");
        Assertions.assertEquals(new BigDecimal(60), updated.getPrice(), "Price should be updated");
    }

    @Test
    @Transactional
    void patchProduct() {
        ProductDto productDto = ProductDto.builder()
                .title("Patch Title")
                .price(new BigDecimal(70))
                .description("Patch Desc")
                .category("Patch Cat")
                .image("patch.jpg")
                .build();
        ProductDto saved = productController.addProduct(productDto);
        ProductDto patchDto = ProductDto.builder()
                .title("Patched Title")
                .price(new BigDecimal(80))
                .build();
        ProductDto patched = productController.patchProduct(saved.getUniqueId(), patchDto);
        Assertions.assertEquals("Patched Title", patched.getTitle(), "Title should be patched");
        Assertions.assertEquals(new BigDecimal(80), patched.getPrice(), "Price should be patched");
    }

    @Test
    @Transactional
    void deleteProduct() {
        ProductDto productDto = ProductDto.builder()
                .title("Delete Title")
                .price(new BigDecimal(90))
                .description("Delete Desc")
                .category("Delete Cat")
                .image("delete.jpg")
                .build();
        ProductDto saved = productController.addProduct(productDto);
        productController.deleteProduct(saved.getUniqueId());
        Optional<Product> deletedProduct = productRepository.findById(saved.getUniqueId());
        deletedProduct.ifPresent(product -> Assertions.assertTrue(product.isDeleted(), "Product should be deleted"));
    }

    @Test
    @Transactional
    void updateStockQuantity() {
        ProductDto productDto = ProductDto.builder()
                .title("Stock Title")
                .price(new BigDecimal(100))
                .description("Stock Desc")
                .category("Stock Cat")
                .image("stock.jpg")
                .build();
        ProductDto saved = productController.addProduct(productDto);
        int newQuantity = 50;
        ProductDto updatedStockProduct = productController.updateStockQuantity(saved.getUniqueId(), newQuantity);
        Assertions.assertEquals(newQuantity, updatedStockProduct.getStockQuantity(), "Stock quantity should be updated");
    }

}
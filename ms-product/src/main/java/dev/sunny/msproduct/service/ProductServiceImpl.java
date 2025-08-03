package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;
    private final String FAKESTORE_BASE_URL = "https://fakestoreapi.com";

    @Override
    public List<ProductDto> findAllProducts() {

        ResponseEntity<Product[]> response;
        try {
            response = restTemplate.exchange(FAKESTORE_BASE_URL + "/products",
                    HttpMethod.GET,
                    null,
                    Product[].class);
        } catch (HttpServerErrorException | HttpClientErrorException hse) {
            throw new RuntimeException("Failed to fetch products: " + hse.getMessage(), hse);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            Assert.notNull(response.getBody(), "Failed to find products");
            List<Product> products = Stream.of(response.getBody()).toList();
            if (!products.isEmpty()) {
                return products.stream()
                        .map(productMapper::toDto)
                        .toList();
            }
        }

        throw new RuntimeException("Failed to fetch products");
    }

    @Override
    public Optional<ProductDto> findProductById(Integer id) {

        ResponseEntity<Product> response;
        try {
            response = restTemplate.getForEntity(FAKESTORE_BASE_URL + "/products/" + id, Product.class);
        } catch (HttpClientErrorException | HttpServerErrorException hse) {
            throw new RuntimeException("Failed to fetch product with id " + id + ": " + hse.getMessage(), hse);
        }

        return response.getBody() != null ? Optional.of(productMapper.toDto(response.getBody())) : Optional.empty();
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {

        Product productToSave = productMapper.toEntity(productDto);

        ResponseEntity<Product> response;
        try {
            response = restTemplate.postForEntity(FAKESTORE_BASE_URL + "/products",
                    productToSave, Product.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            Product savedProduct = response.getBody();
            return productMapper.toDto(savedProduct);
        }
        throw new RuntimeException("Failed to save product, status code: " + response.getStatusCode());

    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto productDto) {

        Product productToUpdate = productMapper.toEntity(productDto);
        ResponseEntity<Product> response;
        try {
            response = restTemplate.exchange(FAKESTORE_BASE_URL + "/products/" + id,
                    HttpMethod.PUT, null, Product.class, productToUpdate);
        } catch (HttpClientErrorException | HttpServerErrorException hse) {
            throw new RuntimeException("Failed to update product with id " + id + ": " + hse.getMessage(), hse);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            Product updatedProduct = response.getBody();
            return productMapper.toDto(updatedProduct);
        }

        throw new RuntimeException("Failed to update product, status code: " + response.getStatusCode());
    }

    @Override
    public void deleteProduct(Integer id) {

        ResponseEntity<Product> response;
        try {
            response = restTemplate.exchange(FAKESTORE_BASE_URL + "/products/" + id,
                    HttpMethod.DELETE, null, Product.class);
        } catch (HttpClientErrorException | HttpServerErrorException hse) {
            throw new RuntimeException("Failed to delete product with id " + id + ": " + hse.getMessage(), hse);
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to delete product, status code: " + response.getStatusCode());
        }

    }
}


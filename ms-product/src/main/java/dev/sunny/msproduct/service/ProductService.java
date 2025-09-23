package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.exceptions.ProductApiException;
import dev.sunny.msproduct.exceptions.ProductDeletedException;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProducts();
    ProductDto findProductById(Long id) throws ProductApiException;
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto) throws ProductApiException;
    void deleteProduct(Long id) throws ProductDeletedException;
}


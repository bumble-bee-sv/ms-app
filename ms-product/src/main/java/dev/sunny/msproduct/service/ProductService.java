package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAllProducts();
    Optional<ProductDto> findProductById(Long id);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}


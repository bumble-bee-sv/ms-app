package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAllProducts();
    Optional<ProductDto> findProductById(Integer id);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(Integer id, ProductDto productDto);
    void deleteProduct(Integer id);
}


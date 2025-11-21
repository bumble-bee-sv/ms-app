package dev.sunny.msproduct.controller;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.exceptions.product.ProductApiException;
import dev.sunny.msproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApis {

    private final ProductService productService;

    @PostMapping("/products/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(@RequestParam(required = false, name = "deleted") boolean deleted) {
        return productService.findAllProducts(deleted);
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable Long id) throws ProductApiException {
        return productService.findProductById(id);
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductApiException {
        return productService.updateProduct(id, productDto);
    }

    @PatchMapping("/products/{id}")
    public ProductDto patchProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductApiException {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductApiException {
        productService.deleteProduct(id);
    }

}

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

    @Override
    @PostMapping("/products/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @Override
    @GetMapping("/products")
    public List<ProductDto> getAllProducts(@RequestParam(required = false, name = "deleted") boolean deleted) {
        return productService.findAllProducts(deleted);
    }

    @Override
    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable Long id) throws ProductApiException {
        return productService.findProductById(id);
    }

    @Override
    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductApiException {
        return productService.updateProduct(id, productDto);
    }

    @Override
    @PatchMapping("/products/{id}")
    public ProductDto patchProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductApiException {
        return productService.updateProduct(id, productDto);
    }

    @Override
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductApiException {
        productService.deleteProduct(id);
    }

    @Override
    @PatchMapping("/products/{id}/update-stock")
    public ProductDto updateStockQuantity(@PathVariable Long id, @RequestParam(name = "quantity") int quantity) throws ProductApiException {
        return productService.updateStockQuantity(id, quantity);
    }

}

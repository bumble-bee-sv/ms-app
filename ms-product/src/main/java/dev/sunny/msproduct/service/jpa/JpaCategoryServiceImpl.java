package dev.sunny.msproduct.service.jpa;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.mappers.CategoryMapper;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.CategoryRepository;
import dev.sunny.msproduct.repository.ProductRepository;
import dev.sunny.msproduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaCategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

//        Save Category
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);

        saveProductsIfPresent(categoryDto, savedCategory);

//        Set Saved Products if any to response
        CategoryDto response = categoryMapper.toDto(savedCategory);
        response.setProducts(savedCategory.getProducts()
                .stream()
                .map(productMapper::toDto)
                .toList());
        return response;

    }

    private void saveProductsIfPresent(CategoryDto categoryDto, Category savedCategory) {
        List<ProductDto> productDto = categoryDto.getProducts();
        if (productDto != null && !productDto.isEmpty()) {
            List<Product> products = productDto.stream()
                    .map(productMapper::toEntity)
                    .toList();
            for (Product product : products) {
                product.setCategory(savedCategory);
            }
            List<Product> savedProducts = productRepository.saveAll(products);
            savedCategory.setProducts(savedProducts);
        } else {
            Optional<List<Product>> existingProducts = productRepository.findAllByCategoryName(savedCategory.getName());
            existingProducts.ifPresent(savedCategory::setProducts);
        }
    }
}

package dev.sunny.msproduct.service.jpa;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;
import dev.sunny.msproduct.exceptions.category.CategoryNameAlreadyExistsException;
import dev.sunny.msproduct.mappers.CategoryMapper;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.CategoryRepository;
import dev.sunny.msproduct.repository.ProductRepository;
import dev.sunny.msproduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaCategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws CategoryApiException {

//        Save Category
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory;
        try {
            savedCategory = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNameAlreadyExistsException("Category with name '" + category.getName() + "' already exists.");
        }

        saveProductsIfPresent(categoryDto, savedCategory);
        return categoryMapper.toDto(savedCategory);

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
        }
    }
}

package dev.sunny.msproduct.service.jpa;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;
import dev.sunny.msproduct.exceptions.category.CategoryNameAlreadyExistsException;
import dev.sunny.msproduct.exceptions.category.CategoryNotFoundException;
import dev.sunny.msproduct.mappers.CategoryMapper;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.CategoryRepository;
import dev.sunny.msproduct.repository.ProductRepository;
import dev.sunny.msproduct.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    @Transactional
    public CategoryDto replaceCategory(Long id, CategoryDto categoryDto) throws CategoryApiException {
        Optional<Category> savedCategory = categoryRepository.findById(id);

        if (savedCategory.isEmpty())
            throw new CategoryNotFoundException("Category with id '" + id + "' not found.");

        Category category = savedCategory.get();
        if (categoryDto.getName() != null) {
            if (!category.getName().equals(categoryDto.getName()))
                category.setName(categoryDto.getName());
            else
                throw new CategoryNameAlreadyExistsException("Category with name '" + category.getName() + "' already exists.");
        }
        category.setDescription(categoryDto.getDescription());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    private void saveProductsIfPresent(CategoryDto categoryDto, Category savedCategory) {
        List<ProductDto> productDtos = categoryDto.getProducts();
        if (productDtos != null && !productDtos.isEmpty()) {
            for (ProductDto productDto : productDtos) {
                // Check if product exists by title and category
                boolean exists = productRepository.existsByTitleAndDeleted(productDto.getTitle(), false);
                if (!exists) {
                    Product product = productMapper.toEntity(productDto);
                    product.setCategory(savedCategory);
                    productRepository.save(product);
                }
            }
        }
    }
}

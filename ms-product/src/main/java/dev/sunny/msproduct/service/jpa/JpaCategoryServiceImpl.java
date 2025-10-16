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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JpaCategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws CategoryApiException {

        String categoryName = categoryDto.getName();
        boolean isExists = categoryRepository.existsByNameAndDeleted(categoryName.toUpperCase(), false);
        if (isExists)
            throw new CategoryNameAlreadyExistsException("Category with name '" + categoryName + "' already exists.");

//        Save Category
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category with id '{}' created.", savedCategory.getId());

        saveProductsIfPresent(categoryDto, savedCategory);
        return categoryMapper.toDto(savedCategory);

    }

    @Override
    @Transactional
    public CategoryDto replaceCategory(Long id, CategoryDto categoryDto) throws CategoryApiException {
        Optional<Category> savedCategory = categoryRepository.findById(id);

        if (savedCategory.isEmpty())
            throw new CategoryNotFoundException("Category with id '" + id + "' not found.");
        else
            log.debug("Category with id '{}' found.", savedCategory.get().getId());

        Category category = savedCategory.get();
        String reqCatName = categoryDto.getName();
        String dbCatName = category.getName();
        if (reqCatName.equalsIgnoreCase(dbCatName))
            throw new CategoryNameAlreadyExistsException("Category with name '" + reqCatName + "' already exists.");

        List<Product> linkedProducts = productRepository.findAllByCategoryAndDeletedFalse(category);
        log.debug("Product(s) linked to category id '{}' fetched. Product ids: {}",
                category.getId(), linkedProducts.stream().map(Product::getId).toList());

//        Soft delete category and link products to new category getting created
        deleteExistingCategory(id, category);

        Category newCategory = Category.builder()
                    .name(reqCatName)
                    .description(categoryDto.getDescription())
                    .products(linkedProducts)
                    .build();

        Category replacedCategory = categoryRepository.save(newCategory);
        productRepository.updateProductCategory(category.getId(), replacedCategory);

        return categoryMapper.toDto(replacedCategory);
    }

    private void deleteExistingCategory(Long id, Category category) {
        if (!category.isDeleted()) {
            category.setDeleted(true);
            category.setDeletedOn(Instant.now());
            category.setProducts(null);
            categoryRepository.save(category);
        } else throw new CategoryNotFoundException("Category with id '" + id + "' is already deleted.");
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws CategoryApiException {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isEmpty()) throw new CategoryNotFoundException("Category with given id '{" + id + "}' not found");
        Category category = existingCategory.get();

        String name = categoryDto.getName();
        String description = categoryDto.getDescription();
//        List<ProductDto> products = categoryDto.getProducts();
        category.setName(name);
        category.setDescription(description);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
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
                    log.info("Product with title '{}' created and linked to category '{}'.",
                            productDto.getTitle(),
                            savedCategory.getName());
                } else {
                    log.info("Product with title '{}' already exists. Skipping creation.", productDto.getTitle());
                }
            }
        }
    }
}

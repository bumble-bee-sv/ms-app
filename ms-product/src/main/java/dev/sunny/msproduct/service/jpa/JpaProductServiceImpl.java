package dev.sunny.msproduct.service.jpa;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.exceptions.ProductApiException;
import dev.sunny.msproduct.exceptions.ProductDeletedException;
import dev.sunny.msproduct.exceptions.ProductNotFoundException;
import dev.sunny.msproduct.exceptions.ProductUpdateFailedException;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.CategoryRepository;
import dev.sunny.msproduct.repository.ProductRepository;
import dev.sunny.msproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> savedProducts = productRepository.findAllByDeleted(false);

        return savedProducts.stream()
                .map(this::mapToDtoWithCategory)
                .toList();
    }

    @Override
    public ProductDto findProductById(Long id) throws ProductApiException {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent() && !product.get().isDeleted()) {
            return mapToDtoWithCategory(product.get());
        }

        throw new ProductNotFoundException("Product not found with id: " + id);
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {

        String categoryName = productDto.getCategory();
        Optional<Category> category = categoryName != null
                ? categoryRepository.findByName(categoryName)
                : Optional.empty();

        Product product = productMapper.toEntity(productDto);
        if (categoryName != null)
            setCategory(productDto, category, product);
        else product.setCategory(null);

        Product savedProduct = productRepository.save(product);

        return mapToDtoWithCategory(savedProduct);

    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) throws ProductApiException {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (productDto != null && existingProduct.isPresent() && !existingProduct.get().isDeleted()) {
            Product product = existingProduct.get();
            String title = productDto.getTitle();
            String description = productDto.getDescription();
            BigDecimal price = productDto.getPrice();
            String image = productDto.getImage();
            String categoryName = productDto.getCategory();

            if (title != null) product.setTitle(title);
            if (description != null) product.setDescription(description);
            if (price != null) product.setPrice(price);
            if (image != null) product.setImage(image);
            if (categoryName != null) {
                Optional<Category> category = categoryRepository.findByName(categoryName);
                setCategory(productDto, category, product);
            }

            Product savedProduct = productRepository.save(product);

            return mapToDtoWithCategory(savedProduct);
        }

        if (existingProduct.isEmpty()) throw new ProductNotFoundException("Product not found with id: " + id);
        if (existingProduct.get().isDeleted()) throw new ProductDeletedException("Product with id " + id + " is deleted.");
        else throw new ProductUpdateFailedException("Updating product failed! Please connect system administrator.");
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) throws ProductApiException {
        boolean isProductExist = productRepository.existsById(id);

        if (!isProductExist) {
            throw new ProductDeletedException("Product with id " + id + " is already deleted");
        }

        productRepository.markProductAsDeleted(id);
    }

    private void setCategory(ProductDto productDto, Optional<Category> category, Product product) {
        if (category.isEmpty()) {
            Category newCategory = Category.builder()
                    .name(productDto.getCategory())
                    .description(productDto.getDescription())
                    .build();
            Category savedCategory = categoryRepository.save(newCategory);
            product.setCategory(savedCategory);
        } else {
            product.setCategory(category.get());
        }
    }

    private ProductDto mapToDtoWithCategory(Product product) {
        ProductDto productDto = productMapper.toDto(product);
        if (product.getCategory() != null) {
            productDto.setCategory(product.getCategory().getName());
        }
        return productDto;
    }
}

package dev.sunny.msproduct.service.jpa;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import dev.sunny.msproduct.mappers.ProductMapper;
import dev.sunny.msproduct.repository.CategoryRepository;
import dev.sunny.msproduct.repository.ProductRepository;
import dev.sunny.msproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service @Primary
@RequiredArgsConstructor
public class JpaProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ProductDto> findProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            ProductDto productDto = productMapper.toDto(product.get());
            return Optional.of(productDto);
        }

        return Optional.empty();
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {

        String categoryName = productDto.category();
        Optional<Category> category = categoryName != null
                ? categoryRepository.findByName(categoryName)
                : Optional.empty();

        Product product = productMapper.toEntity(productDto);
        if (categoryName != null)
            setCategory(productDto, category, product);

        return productMapper.toDto(productRepository.save(product));

    }

    private void setCategory(ProductDto productDto, Optional<Category> category, Product product) {
        if (category.isEmpty()) {
            Category newCategory = Category.builder()
                    .name(productDto.category())
                    .description(productDto.category())
                    .build();
            Category savedCategory = categoryRepository.save(newCategory);
            product.setCategory(savedCategory);
        } else {
            product.setCategory(category.get());
        }
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (productDto != null && existingProduct.isPresent()) {
            Product product = existingProduct.get();
            String title = productDto.title();
            String description = productDto.description();
            BigDecimal price = productDto.price();
            String image = productDto.image();
            String categoryName = productDto.category();

            if (title != null) product.setTitle(title);
            if (description != null) product.setDescription(description);
            if (price != null) product.setPrice(price);
            if (image != null) product.setImage(image);
            if (categoryName != null) {
                Optional<Category> category = categoryRepository.findByName(categoryName);
                setCategory(productDto, category, product);
            }

            return productMapper.toDto(productRepository.save(product));
        }

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
    }
}

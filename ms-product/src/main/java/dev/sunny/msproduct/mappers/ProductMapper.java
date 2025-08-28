package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ProductMapper {

    @Mapping(source = "id", target = "uniqueId")
    @Mapping(source = "category.name", target = "category")
    @Mapping(target = "createdOn", expression = "java(toLocalDate(product.getCreatedOn()))")
    @Mapping(target = "modifiedOn", expression = "java(toLocalDate(product.getModifiedOn()))")
    ProductDto toDto(Product product);
    @Mapping(target = "category", expression = "java(blankOrNullCategoryMapping(productDto.category()))")
    Product toEntity(ProductDto productDto);

    default LocalDateTime toLocalDate(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }

    default Category blankOrNullCategoryMapping(String category) {
        if (category != null && !category.isEmpty()) {
            Category toBeSaved = new Category();
            toBeSaved.setName(category);
            return toBeSaved;
        }

        return null;
    }

}

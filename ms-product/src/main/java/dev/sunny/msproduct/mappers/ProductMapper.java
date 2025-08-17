package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "uniqueId")
    @Mapping(target = "createdOn", expression = "java(toLocalDate(product.getCreatedOn()))")
    @Mapping(target = "modifiedOn", expression = "java(toLocalDate(product.getModifiedOn()))")
    @Mapping(source = "category.name", target = "category", nullValuePropertyMappingStrategy = IGNORE)
    ProductDto toDto(Product product);
    @Mapping(target = "category.name", source = "category", nullValuePropertyMappingStrategy = IGNORE)
    Product toEntity(ProductDto productDto);

    default LocalDateTime toLocalDate(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }

}

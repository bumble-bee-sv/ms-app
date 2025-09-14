package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, uses = {DateTimeMapper.class})
public interface ProductMapper {

    @Mapping(target = "category", expression = "java(product.getCategory() != null ? product.getCategory().getName() : \"NA\")")
    @Mapping(target = "uniqueId", source = "id")
    ProductDto toDto(Product product);
    @Mapping(target = "category.name", source = "category")
    @InheritInverseConfiguration
    Product toEntity(ProductDto productDto);

}

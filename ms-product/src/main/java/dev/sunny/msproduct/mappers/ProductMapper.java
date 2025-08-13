package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "uniqueId")
    @Mapping(source = "category.name", target = "category",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductDto toDto(Product product);
    @Mapping(target = "category.name", source = "category",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product toEntity(ProductDto productDto);

}

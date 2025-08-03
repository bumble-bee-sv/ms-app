package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "uniqueId")
    ProductDto toDto(Product product);
    @Mapping(target = "id", source = "uniqueId")
    Product toEntity(ProductDto productDto);

}

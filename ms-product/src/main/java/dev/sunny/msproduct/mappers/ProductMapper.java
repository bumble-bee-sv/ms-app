package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.ProductDto;
import dev.sunny.msproduct.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ProductMapper {

    @Mapping(source = "id", target = "uniqueId")
    @Mapping(target = "category", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
    @Mapping(target = "createdOn", expression = "java(toLocalDate(product.getCreatedOn()))")
    @Mapping(target = "modifiedOn", expression = "java(toLocalDate(product.getModifiedOn()))")
    ProductDto toDto(Product product);
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto productDto);

    default LocalDateTime toLocalDate(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }

}

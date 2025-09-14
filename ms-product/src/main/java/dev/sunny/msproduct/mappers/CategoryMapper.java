package dev.sunny.msproduct.mappers;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.entity.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, uses = {DateTimeMapper.class})
public interface CategoryMapper {

    @Mapping(source = "uniqueId", target = "id")
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    @InheritInverseConfiguration
    @Mapping(target = "products", ignore = true)
    CategoryDto toDto(Category category);

}

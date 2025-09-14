package dev.sunny.msproduct.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
public class CategoryDto extends BaseDto {
    private String name;
    private String description;
    private List<ProductDto> products;

    @Builder
    public CategoryDto(Long uniqueId, LocalDateTime createdOn, LocalDateTime modifiedOn, boolean deleted,
                       String name, String description, List<ProductDto> products) {
        super(uniqueId, createdOn, modifiedOn, deleted);
        this.name = name;
        this.description = description;
        if (products != null && !products.isEmpty())
            this.products = products;
    }
}

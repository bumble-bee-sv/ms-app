package dev.sunny.msproduct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uniqueId", "name", "description", "products", "createdOn", "modifiedOn", "deletedOn", "deleted"})
public class CategoryDto extends BaseDto {
    private String name;
    private String description;
    private List<ProductDto> products;

    @Builder
    public CategoryDto(Long uniqueId, LocalDateTime createdOn, LocalDateTime modifiedOn, LocalDateTime deletedOn,
                       boolean deleted, String name, String description, List<ProductDto> products) {
        super(uniqueId, createdOn, modifiedOn, deletedOn, deleted);
        this.name = name;
        this.description = description;
        if (products != null && !products.isEmpty())
            this.products = products;
    }
}

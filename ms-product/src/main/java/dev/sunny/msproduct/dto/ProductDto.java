package dev.sunny.msproduct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @ToString @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uniqueId", "title", "price", "description", "category", "image", "createdOn", "modifiedOn", "deletedOn", "deleted" })
@Schema(
        name = "ProductRequest",
        description = "It holds the necessary information for creating or updating a product.",
        requiredProperties = { "title", "price"}
)
public class ProductDto extends BaseDto {
    private String title;
    private BigDecimal price;
    private String description;
    private String category;
    private String image;

    @Builder
    public ProductDto(Long uniqueId, LocalDateTime createdOn, LocalDateTime modifiedOn, LocalDateTime deletedOn,
                      boolean deleted, String title, BigDecimal price, String description, String category, String image) {
        super(uniqueId, createdOn, modifiedOn, deletedOn, deleted);
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }
}
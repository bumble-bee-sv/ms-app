package dev.sunny.msproduct.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @ToString @NoArgsConstructor
public class ProductDto extends BaseDto {
    private String title;
    private BigDecimal price;
    private String description;
    private String category;
    private String image;

    @Builder
    public ProductDto(Long uniqueId, LocalDateTime createdOn, LocalDateTime modifiedOn, boolean deleted, String title, BigDecimal price, String description, String category, String image) {
        super(uniqueId, createdOn, modifiedOn, deleted);
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }
}
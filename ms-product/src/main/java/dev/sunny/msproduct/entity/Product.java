package dev.sunny.msproduct.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(indexes = {
        @Index(name = "IDX_PROD_ID_CAT_ID", columnList = "id, category_id"),
        @Index(name = "IDX_PROD_ID_MODIFIED_ON_DEL", columnList = "id, modifiedOn, deleted")
})
@Getter @Setter @NoArgsConstructor @ToString
public class Product extends BaseEntity {

    private String title;
    private BigDecimal price;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;
    private String image;

    @Builder
    public Product(Long id, Instant createdOn, Instant modifiedOn, boolean deleted, String title, BigDecimal price, String description, Category category, String image) {
        super(id, createdOn, modifiedOn, deleted);
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }

}


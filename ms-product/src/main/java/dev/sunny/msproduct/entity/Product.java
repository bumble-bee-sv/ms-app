package dev.sunny.msproduct.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(indexes = {
        @Index(name = "IDX_CAT_ID_DEL", columnList = "category_id, deleted"),
        @Index(name = "IDX_PROD_ID_CAT_ID_DEL", columnList = "id, category_id, deleted"),
        @Index(name = "IDX_PROD_ID_MODIFIED_ON_DEL", columnList = "id, modifiedOn, deleted")
})
@Getter @Setter @NoArgsConstructor @ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;
    private String image;
    private boolean stockAvailability;
    private int stockQuantity;

    @Builder
    public Product(Long id, Instant createdOn, Instant modifiedOn, Instant deletedOn, boolean deleted,
                   String title, BigDecimal price, String description, Category category, String image,
                   boolean stockAvailability, int stockQuantity) {
        super(createdOn, modifiedOn, deletedOn, deleted);
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.stockAvailability = stockAvailability;
        this.stockQuantity = stockQuantity;
    }

}


package dev.sunny.msproduct.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "IDX_CATEGORY_NAME_UNQ", columnList = "name", unique = true)
})
@Getter @Setter @NoArgsConstructor @ToString
public class Category extends BaseEntity {

    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Product> products;

    @Builder
    public Category(Long id, Instant createdOn, Instant modifiedOn, boolean deleted, String name, String description, List<Product> products) {
        super(id, createdOn, modifiedOn, deleted);
        this.name = name;
        this.description = description;
        if (products != null && !products.isEmpty())
            this.products = products;
    }
}

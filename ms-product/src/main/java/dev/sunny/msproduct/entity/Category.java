package dev.sunny.msproduct.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "IDX_CATEGORY_NAME_UNQ", columnList = "name", unique = true)
})
@Getter @Setter @NoArgsConstructor @ToString
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Product> products;

    @Builder
    public Category(Long id, Instant createdOn, Instant modifiedOn, Instant deletedOn, boolean deleted, String name, String description, List<Product> products) {
        super(createdOn, modifiedOn, deletedOn, deleted);
        this.id = id;
        this.name = name;
        this.description = description;
        if (products != null && !products.isEmpty())
            this.products = products;
    }
}

package dev.sunny.msproduct.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class Category extends  BaseEntity {

    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Product> products;

}

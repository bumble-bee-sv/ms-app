package dev.sunny.msproduct.repository;

import dev.sunny.msproduct.entity.Category;
import dev.sunny.msproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByDeleted(boolean deleted);

    boolean existsByTitleAndDeleted(String title, boolean deleted);

    Optional<Product> findByTitleAndDeletedAndDeletedOnIsNull(String title, boolean deleted);

    @Modifying
    @Query("UPDATE Product p SET p.category = :replacedCategory WHERE p.category.id = :id AND p.deleted = false")
    void updateProductCategory(Long id, Category replacedCategory);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.deleted = false")
    List<Product> findAllByCategoryAndDeletedFalse(Category category);

    Optional<Product> findByIdAndDeletedFalse(Long id);
}


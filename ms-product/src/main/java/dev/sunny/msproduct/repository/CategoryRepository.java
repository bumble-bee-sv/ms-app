package dev.sunny.msproduct.repository;

import dev.sunny.msproduct.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    boolean existsByNameAndDeleted(String name, boolean deleted);

    Optional<Category> findByNameAndDeletedFalse(String categoryName);

    List<Category> findCategoriesByDeleted(boolean deleted);

    @Query("SELECT c.name FROM Category c WHERE c.deleted = :deleted")
    List<String> findCategoryNamesByDeleted(boolean deleted);

}

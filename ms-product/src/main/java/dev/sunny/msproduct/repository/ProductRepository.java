package dev.sunny.msproduct.repository;

import dev.sunny.msproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByCategoryName(String name);

    List<Product> findAllByDeleted(boolean deleted);

    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = ?1")
    void markProductAsDeleted(Long id);
}


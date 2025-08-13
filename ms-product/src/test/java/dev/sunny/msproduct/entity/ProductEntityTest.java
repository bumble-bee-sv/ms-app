package dev.sunny.msproduct.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ProductEntityTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    void testProductTableCreation() {

//        Check if starting application context creates the correct tables
        assertNotNull(entityManager);

        // Check if Product table is created
        var productTable = entityManager.getMetamodel()
                .entity(Product.class);
        assertNotNull(productTable);

        // Check if Category table is created
        var categoryTable = entityManager.getMetamodel()
                .entity(Category.class);
        assertNotNull(categoryTable);

        // Check if Product has a ManyToOne relationship with Category
        assertNotNull(productTable.getAttribute("category"));

    }
}
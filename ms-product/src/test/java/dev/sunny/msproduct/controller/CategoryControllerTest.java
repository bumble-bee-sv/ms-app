package dev.sunny.msproduct.controller;

import dev.sunny.msproduct.dto.CategoryDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class CategoryControllerTest {

    MockMvc mockMvc;

    @Autowired
    CategoryController categoryController;
    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Rollback
    @Transactional
    void addCategory() {

        CategoryDto categoryDto = CategoryDto.builder()
                .name("test")
                .description("test category")
                .deleted(false)
                .build();

        CategoryDto savedCategoryDto = categoryController.addCategory(categoryDto);
        assertNotNull(savedCategoryDto);
        assertNotNull(savedCategoryDto.getUniqueId(), "Saved Category ID should not be null");

        assertEquals(categoryDto.getName().toUpperCase(), savedCategoryDto.getName());
    }
}

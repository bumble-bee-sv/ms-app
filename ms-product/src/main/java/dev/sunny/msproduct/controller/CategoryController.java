package dev.sunny.msproduct.controller;


import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;
import dev.sunny.msproduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories/add")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) throws CategoryApiException {
        return categoryService.createCategory(categoryDto);
    }

}

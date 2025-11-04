package dev.sunny.msproduct.controller;


import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;
import dev.sunny.msproduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories/add")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) throws CategoryApiException {
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping("/categories/{id}")
    public CategoryDto patchCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) throws CategoryApiException {
        return categoryService.updateCategory(id, categoryDto);
    }

    @PutMapping("/categories/update/{id}")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody CategoryDto categoryDto) throws CategoryApiException {
        return categoryService.replaceCategory(id, categoryDto);
    }

}

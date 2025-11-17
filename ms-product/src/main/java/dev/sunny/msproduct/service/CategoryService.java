package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto) throws CategoryApiException;
    List<CategoryDto> listAllCategories(boolean deleted) throws CategoryApiException;
    List<String> listAllCategoryNames(boolean deleted) throws CategoryApiException;
    CategoryDto replaceCategory(Long id, CategoryDto categoryDto) throws CategoryApiException;
    CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws CategoryApiException;
    CategoryDto getCategoryById(Long id, boolean deleted, boolean viewProducts) throws CategoryApiException;

}

package dev.sunny.msproduct.service;

import dev.sunny.msproduct.dto.CategoryDto;
import dev.sunny.msproduct.exceptions.category.CategoryApiException;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto) throws CategoryApiException;

}

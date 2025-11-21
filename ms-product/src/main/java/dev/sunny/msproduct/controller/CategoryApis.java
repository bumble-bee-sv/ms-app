package dev.sunny.msproduct.controller;

import dev.sunny.msproduct.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Category API Endpoints",
        description = "APIs for managing product categories in store"
)
public interface CategoryApis {

    @PostMapping("/categories")
    @Operation(summary = "Add a category", description = "Create a new product category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category data")
    })
    CategoryDto addCategory(@RequestBody CategoryDto categoryDto);

    @PatchMapping("/categories/{id}")
    @Operation(summary = "Patch a category", description = "Partially update an existing category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category patched successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid category data")
    })
    CategoryDto patchCategory(
            @Parameter(description = "ID of the category to patch")
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto);

    @PutMapping("/categories/{id}")
    @Operation(summary = "Update a category", description = "Replace an existing category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid category data")
    })
    CategoryDto updateCategory(
            @Parameter(description = "ID of the category to update")
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto);

    @GetMapping("/categories")
    @Operation(summary = "Get categories", description = "Retrieve all categories; optionally filter by deleted status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of categories returned")
    })
    List<CategoryDto> getCategories(
            @Parameter(description = "Filter by deleted status")
            @RequestParam(required = false, name = "deleted") boolean deleted);

    @GetMapping("/categories/names")
    @Operation(summary = "Get category names", description = "Retrieve names of categories; optionally filter by deleted status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of category names returned")
    })
    List<String> getCategoryNames(
            @Parameter(description = "Filter by deleted status")
            @RequestParam(required = false, name = "deleted") boolean deleted);

    @GetMapping("/categories/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a category by id with options to include deleted or view associated products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category returned"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    CategoryDto getCategoryById(
            @Parameter(description = "ID of the category to retrieve")
            @PathVariable Long id,
            @Parameter(description = "Include deleted products in search")
            @RequestParam(required = false, name = "deleted") boolean deleted,
            @Parameter(description = "Whether to include products in the returned category")
            @RequestParam(required = false, name = "viewProducts") boolean viewProducts);

}

package dev.sunny.msproduct.controller;

import dev.sunny.msproduct.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Product API Endpoints",
        description = "APIs for managing products in store"
)
public interface ProductApis {

    @Operation(
            summary = "Add a new product",
            description = "Creates a new product in the store"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    ProductDto addProduct(ProductDto productDto);

    @Operation(
            summary = "Get all products",
            description = "Retrieves all products from the store. Optionally filter by deleted status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products returned")
    })
    List<ProductDto> getAllProducts(@Parameter(description = "Filter by deleted status") boolean deleted);

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a product by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ProductDto getProductById(@Parameter(description = "ID of the product to retrieve") Long id);

    @Operation(
            summary = "Update product",
            description = "Updates an existing product by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    ProductDto updateProduct(@Parameter(description = "ID of the product to update") Long id, ProductDto productDto);

    @Operation(
            summary = "Patch product",
            description = "Partially updates an existing product by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product patched successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    ProductDto patchProduct(@Parameter(description = "ID of the product to patch") Long id, ProductDto productDto);

    @Operation(
            summary = "Delete product",
            description = "Deletes a product by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    void deleteProduct(@Parameter(description = "ID of the product to delete") Long id);

}

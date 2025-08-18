package com.movieflix.controller.documentation;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Methods responsible for managing categories.")
public interface CategoryControllerDoc {

    @Operation(
            summary = "Find all categories",
            description = "Method responsible for returning all registered categories.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200", description = "Categories successfully listed",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))
    )
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    @Operation(
            summary = "Save category",
            description = "Method responsible for saving a new category.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "201", description = "Category saved successfully",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class))
    )
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PostMapping
    ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest request);

    @Operation(
            summary = "Update category",
            description = "Method responsible for updating an existing category.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200", description = "Category updated successfully",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PutMapping("/{id}")
    ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request);

    @Operation(
            summary = "Find category by ID",
            description = "Method responsible for returning a specific category by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200", description = "Category found successfully",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> getByCategoryId(@PathVariable Long id);

    @Operation(
            summary = "Delete category",
            description = "Method responsible for deleting a category by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategoryById(@PathVariable Long id);
}
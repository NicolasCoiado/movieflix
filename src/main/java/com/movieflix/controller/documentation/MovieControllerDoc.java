package com.movieflix.controller.documentation;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
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

@Tag(name = "Movie", description = "Methods responsible for managing movies.")
public interface MovieControllerDoc {

    @Operation(
            summary = "Save movie",
            description = "Method responsible for saving a new movie."
    )
    @ApiResponse(responseCode = "201", description = "Movie saved successfully",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PostMapping
    ResponseEntity<MovieResponse> save(@Valid @RequestBody MovieRequest request);

    @Operation(
            summary = "Find all movies",
            description = "Method responsible for returning all registered movies.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Movies successfully listed",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping
    ResponseEntity<List<MovieResponse>> findAll();

    @Operation(
            summary = "Find movie by ID",
            description = "Method responsible for returning a specific movie by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Movie found successfully",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping("/{id}")
    ResponseEntity<MovieResponse> findById(@PathVariable Long id);

    @Operation(
            summary = "Update movie",
            description = "Method responsible for updating an existing movie.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Movie updated successfully",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PutMapping("/{id}")
    ResponseEntity<MovieResponse> update(@PathVariable Long id, @Valid @RequestBody MovieRequest request);

    @Operation(
            summary = "Edit movie",
            description = "Method responsible for partially updating an existing movie.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Movie edited successfully",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PatchMapping("/{id}")
    ResponseEntity<MovieResponse> edit(@PathVariable Long id, @Valid @RequestBody MovieRequest request);

    @Operation(
            summary = "Search movies by category",
            description = "Method responsible for finding movies by category IDs.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Movies found successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping("/search")
    ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam List<Long> categoriesIds);

    @Operation(
            summary = "Delete movie",
            description = "Method responsible for deleting a movie by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Movie deleted successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @Operation(
            summary = "Generate movie image",
            description = "Method responsible for generating an image for a specific movie by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Image generated successfully",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PostMapping("/generate/{id}")
    ResponseEntity<String> generateImg(@PathVariable Long id);
}
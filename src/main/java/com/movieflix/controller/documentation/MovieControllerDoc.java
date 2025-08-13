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

@Tag(name = "Movie", description = "Method responsible for managing movies.")
public interface MovieControllerDoc {
    @Operation(summary = "Save movie", description = "Método responsável por realizar o salvamento de um novo filme.")
    @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso",
            content = @Content(schema = @Schema(implementation = MovieResponse.class))
    )
    @PostMapping
    ResponseEntity<MovieResponse> save (@Valid @RequestBody MovieRequest request);
    @Operation(
            summary = "Buscar filmes",
            description = "Método responsável por retornar todos os filmes cadastrados.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class)))
    )
    @GetMapping
    ResponseEntity<List<MovieResponse>> findAll ();

    @Operation(
            summary = "Descrever filme",
            description = "Método responsável por retornar todos os filmes cadastrados.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class)))
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById (@PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update (@PathVariable Long id, @Valid @RequestBody MovieRequest request);

    @GetMapping("/search/")
    ResponseEntity<List<MovieResponse>> findByCategory (@RequestParam  List<Long> categoriesIds);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete (@PathVariable Long id);
}

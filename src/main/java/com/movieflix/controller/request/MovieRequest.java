package com.movieflix.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        @NotBlank(message = "The movie title is required.")
        @Schema(type = "String", description = "Movie name")
        String title,
        @Schema(type = "String", description = "Movie description")
        String description,
        @Schema(type = "date", description = "Movie release date")
        @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "double", description = "Movie rating. Eg: 6.6")
        double rating,
        @Schema(type = "array", description = "List of category codes")
        List<Long> categories,
        @Schema(type = "array", description = "List of streaming service codes")
        List<Long> streamings
) {

}

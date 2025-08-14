package com.movieflix.controller.documentation;

import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
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

@Tag(name = "Streaming", description = "Methods responsible for managing streaming platforms.")
public interface StreamingControllerDoc {

    @Operation(
            summary = "Find all streaming platforms",
            description = "Method responsible for returning all registered streaming platforms.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200", description = "Streaming platforms successfully listed",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StreamingResponse.class)))
    )
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping
    ResponseEntity<List<StreamingResponse>> getAll();

    @Operation(
            summary = "Save streaming platform",
            description = "Method responsible for saving a new streaming platform.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "201", description = "Streaming platform saved successfully",
            content = @Content(schema = @Schema(implementation = StreamingResponse.class))
    )
    @ApiResponse(responseCode = "500", description = "Internal error")
    @PostMapping
    ResponseEntity<StreamingResponse> save(@Valid @RequestBody StreamingRequest request);

    @Operation(
            summary = "Find streaming platform by ID",
            description = "Method responsible for returning a specific streaming platform by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200", description = "Streaming platform found successfully",
            content = @Content(schema = @Schema(implementation = StreamingResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Streaming platform not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping("/{id}")
    ResponseEntity<StreamingResponse> getById(@PathVariable Long id);

    @Operation(
            summary = "Delete streaming platform",
            description = "Method responsible for deleting a streaming platform by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Streaming platform deleted successfully")
    @ApiResponse(responseCode = "404", description = "Streaming platform not found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Long id);
}

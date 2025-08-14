package com.movieflix.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StreamingRequest(
        @NotBlank(message = "The name of the streaming service is required.")
        String name
) {
}

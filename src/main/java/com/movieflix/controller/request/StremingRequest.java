package com.movieflix.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StremingRequest(@NotEmpty(message = "Nome do serviço de streaming é obrigatório.") String name) {
}

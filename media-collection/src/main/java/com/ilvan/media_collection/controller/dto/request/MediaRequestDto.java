package com.ilvan.media_collection.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MediaRequestDto(@NotEmpty(message = "name field must not be empty") String name,
                              @NotNull(message = "seasons field cannot be null") Integer seasons,
                              @NotNull(message = "idTypeMedia field cannot be null") Long idTypeMedia,
                              @NotNull(message = "idProductionStatus field cannot be null") Long idProductionStatus,
                              @NotNull(message = "idWatchingStatus field cannot be null") Long idWatchingStatus) {
}

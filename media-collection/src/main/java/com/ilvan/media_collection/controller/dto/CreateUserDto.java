package com.ilvan.media_collection.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(@NotBlank String email, @NotBlank @Size(min = 8) String password) {
}

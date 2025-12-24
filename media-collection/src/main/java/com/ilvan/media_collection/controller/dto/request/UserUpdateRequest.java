package com.ilvan.media_collection.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(@NotBlank String email, @NotBlank String currentPassword,@NotBlank @Size(min = 8) String newPassword) {
}

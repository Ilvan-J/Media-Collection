package com.ilvan.media_collection.controller.dto.response;

import java.util.UUID;

public record UserResponseDto(UUID id, String email, String password) {
}

package com.ilvan.media_collection.controller.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record MediaResponseDto(UUID idMedia,
                               String name,
                               Integer seasons,
                               String nameTypeMedia,
                               String nameProductionStatus,
                               String nameWatchingStatus,
                               String userEmail,
                               LocalDateTime dateOfAdded,
                               LocalDateTime modificationDate) {
}

package com.ilvan.media_collection.controller.dto.request;

public record MediaRequestDto(String name,
                              Integer seasons,
                              Long idTypeMedia,
                              Long idProductionStatus,
                              Long idWatchingStatus) {
}

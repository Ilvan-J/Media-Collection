package com.ilvan.media_collection.controller.dto;

import java.util.UUID;

public record CreateMediaDto (String name,
                              Integer seasons,
                              Long idTypeMedia,
                              Long idProductionStatus,
                              Long watchingStatus, UUID idUser){
}

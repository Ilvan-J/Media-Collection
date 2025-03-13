package com.ilvan.media_collection.services;

import com.ilvan.media_collection.controller.dto.request.MediaRequestDto;
import com.ilvan.media_collection.controller.dto.response.MediaResponseDto;
import com.ilvan.media_collection.controller.erros.CustomGenericException;
import com.ilvan.media_collection.entities.Media;
import com.ilvan.media_collection.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final TypeMediaRepository typeMediaRepository;
    private final ProductionStatusRepository productionStatusRepository;
    private final WatchingStatusRepository watchingStatusRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MediaService(MediaRepository mediaRepository,
                        TypeMediaRepository typeMediaRepository,
                        ProductionStatusRepository productionStatusRepository,
                        WatchingStatusRepository watchingStatusRepository,
                        UserRepository userRepository,
                        ModelMapper modelMapper) {
        this.mediaRepository = mediaRepository;
        this.typeMediaRepository = typeMediaRepository;
        this.productionStatusRepository = productionStatusRepository;
        this.watchingStatusRepository = watchingStatusRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveMedia(MediaRequestDto dto, JwtAuthenticationToken token) {

        var media = modelMapper.map(dto, Media.class);

        var typeMedia = typeMediaRepository.findById(dto.idTypeMedia())
                .orElseThrow(() -> new CustomGenericException("Type media not found", HttpStatus.NOT_FOUND));

        var productionStatus = productionStatusRepository.findById(dto.idProductionStatus())
                .orElseThrow(() -> new CustomGenericException("Production status not found", HttpStatus.NOT_FOUND));

        var watchingStatus = watchingStatusRepository.findById(dto.idWatchingStatus())
                .orElseThrow(() -> new CustomGenericException("Watching status not found", HttpStatus.NOT_FOUND));

        var user = userRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new CustomGenericException("User not found", HttpStatus.NOT_FOUND));

        media.setTypeMedia(typeMedia);
        media.setProductionStatus(productionStatus);
        media.setWatchingStatus(watchingStatus);
        media.setUser(user);
        media.setName(dto.name());
        media.setSeasons(dto.seasons());

        mediaRepository.save(media);
    }

    @Transactional
    public Page<MediaResponseDto> getAllMedias(Pageable pageable, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new CustomGenericException("User not found", HttpStatus.NOT_FOUND));

        var mediaPage = mediaRepository.findByUser(pageable, user);

        return mediaPage.map(media -> new MediaResponseDto(
                media.getIdMedia(),
                media.getName(),
                media.getSeasons(),
                media.getTypeMedia().getName(),
                media.getProductionStatus().getName(),
                media.getWatchingStatus().getName(),
                media.getUser().getEmail(),
                media.getDateOfAdded(),
                media.getModificationDate()
        ));
    }
}

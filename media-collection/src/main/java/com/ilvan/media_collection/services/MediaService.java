package com.ilvan.media_collection.services;

import com.ilvan.media_collection.controller.dto.request.MediaRequestDto;
import com.ilvan.media_collection.controller.dto.response.MediaResponseDto;
import com.ilvan.media_collection.controller.erros.CustomGenericException;
import com.ilvan.media_collection.entities.Media;
import com.ilvan.media_collection.entities.ProductionStatus;
import com.ilvan.media_collection.entities.TypeMedia;
import com.ilvan.media_collection.entities.WatchingStatus;
import com.ilvan.media_collection.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.tokens.Token;

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
    public Page<MediaResponseDto> findWithFilterAndSorting(Long idTypeMedia,
                                                           Long idProductionStatus,
                                                           Long idWatchingStatus,
                                                           String name,
                                                           String orderBy,
                                                           String orderDirection,
                                                           PageRequest pageable,
                                                           JwtAuthenticationToken token) {

        var userId = UUID.fromString(token.getName());

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomGenericException("User not found", HttpStatus.NOT_FOUND));

        TypeMedia typeMedia = null;

        if (idTypeMedia != null) {
            typeMedia = typeMediaRepository.findById(idTypeMedia)
                    .orElseThrow(() -> new CustomGenericException("Type media not found", HttpStatus.NOT_FOUND));
        }

        ProductionStatus productionStatus = null;

        if (idProductionStatus != null) {
            productionStatus = productionStatusRepository.findById(idProductionStatus)
                    .orElseThrow(() -> new CustomGenericException("Production Status not found", HttpStatus.NOT_FOUND));
        }

        WatchingStatus watchingStatus = null;

        if (idWatchingStatus != null) {
            watchingStatus = watchingStatusRepository.findById(idWatchingStatus)
                    .orElseThrow(() -> new CustomGenericException("Watchin status not found", HttpStatus.NOT_FOUND));
        }

        var orderByProperty = "dateOfAdded";

        if (orderBy != null && (orderBy.equalsIgnoreCase("dateOfAdded")) || orderBy.equalsIgnoreCase("modificationDate")) {
            orderByProperty = orderBy;
        }

        var direction = orderDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        var dynamicPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, orderByProperty);

        var mediaPage = mediaRepository.findByUserWithFilters(user, typeMedia, productionStatus, watchingStatus, name, dynamicPageable);

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

    @Transactional
    public MediaResponseDto getMediaById(UUID id, JwtAuthenticationToken token) {
        var media = mediaRepository.findById(id)
                .orElseThrow(() -> new CustomGenericException("Media not found!", HttpStatus.NOT_FOUND));

        var userIdReceived = UUID.fromString(token.getToken().getSubject());

        if (!media.getUser().getUserId().equals(userIdReceived)) {
            throw new CustomGenericException("You are not authorized to read ", HttpStatus.FORBIDDEN);
        }

        return new MediaResponseDto(media.getIdMedia(),
                media.getName(),
                media.getSeasons(),
                media.getTypeMedia().getName(),
                media.getProductionStatus().getName(),
                media.getWatchingStatus().getName(),
                media.getUser().getEmail(),
                media.getDateOfAdded(),
                media.getModificationDate());
    }

    @Transactional
    public void updateMedia(UUID idMedia, MediaRequestDto dto, JwtAuthenticationToken token) {

        var media = modelMapper.map(dto, Media.class);

        media = mediaRepository.findById(idMedia)
                .orElseThrow(() -> new CustomGenericException("Media not found", HttpStatus.NOT_FOUND));

        var userIdReceived = UUID.fromString(token.getToken().getSubject());

        if (!media.getUser().getUserId().equals(userIdReceived)) {
            throw new CustomGenericException("You are not authorized to update this media", HttpStatus.FORBIDDEN);
        }

        var typeMedia = typeMediaRepository.findById(dto.idTypeMedia())
                .orElseThrow(() -> new CustomGenericException("Type media not found", HttpStatus.NOT_FOUND));

        var productionStatus = productionStatusRepository.findById(dto.idProductionStatus())
                .orElseThrow(() -> new CustomGenericException("Production status not found", HttpStatus.NOT_FOUND));

        var watchingStatus = watchingStatusRepository.findById(dto.idWatchingStatus())
                .orElseThrow(() -> new CustomGenericException("Watching status not found", HttpStatus.NOT_FOUND));

        media.setIdMedia(idMedia);
        media.setName(dto.name());
        media.setSeasons(dto.seasons());
        media.setTypeMedia(typeMedia);
        media.setProductionStatus(productionStatus);
        media.setWatchingStatus(watchingStatus);

        mediaRepository.save(media);
    }

    @Transactional
    public void deleteMedia(UUID id, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getToken().getSubject());
        var media = mediaRepository.findById(id)
                .orElseThrow(() -> new CustomGenericException("Media not found", HttpStatus.NOT_FOUND));

        if (!media.getUser().getUserId().equals(userId)) {
            throw new CustomGenericException("You are not authorized to delete this media", HttpStatus.FORBIDDEN);
        }

        mediaRepository.delete(media);
    }
}

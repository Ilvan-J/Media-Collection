package com.ilvan.media_collection.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.ilvan.media_collection.controller.dto.request.MediaRequestDto;
import com.ilvan.media_collection.controller.dto.response.MediaResponseDto;
import com.ilvan.media_collection.services.MediaService;

import java.util.UUID;

@RestController
@RequestMapping("/api/media-collection/medias")
@SecurityRequirement(name = "Bearer Authentication")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> newMedia(@RequestBody @Valid MediaRequestDto dto, JwtAuthenticationToken token) {
        mediaService.saveMedia(dto, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list-all")
    public ResponseEntity<Page<MediaResponseDto>> getAllMedia(
                                                   @RequestParam(value = "typeMedia", required = false) Long typeMedia,
                                                   @RequestParam(value = "productionStatus", required = false) Long productionStatus,
                                                   @RequestParam(value = "watchingStatus", required = false) Long watchingStatus,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "orderBy", defaultValue = "dateOfAdded") String orderBy,
                                                   @RequestParam(value = "orderDirection", defaultValue = "desc") String orderDirection,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                   JwtAuthenticationToken token) {
        var pageable = PageRequest.of(page, pageSize);

        var medias = mediaService.findWithFilterAndSorting(
                typeMedia, productionStatus, watchingStatus, name, orderBy, orderDirection, pageable,token
        );
        return ResponseEntity.status(HttpStatus.OK).body(medias);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<MediaResponseDto> getMediaById(@PathVariable(name = "id") UUID id, JwtAuthenticationToken token) {
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.getMediaById(id, token));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateMedia(@PathVariable(name = "id") UUID idMedia,
                                            @RequestBody MediaRequestDto dto, JwtAuthenticationToken token) {
        mediaService.updateMedia(idMedia, dto, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable(name = "id") UUID id, JwtAuthenticationToken token) {
        mediaService.deleteMedia(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

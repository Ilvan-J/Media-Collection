package com.ilvan.media_collection.controller;

import com.ilvan.media_collection.controller.dto.TypeMediaResponseDTO;
import com.ilvan.media_collection.services.TypeMediaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/media-collection/type-media")
@SecurityRequirement(name = "Bearer Authentication")
public class TypeMediaController {

    private final TypeMediaService typeMediaService;

    public TypeMediaController(TypeMediaService typeMediaService) {
        this.typeMediaService = typeMediaService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<TypeMediaResponseDTO>> getAllMedias(@PageableDefault(page = 0,
            size = 10,
            sort = "name",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                                   JwtAuthenticationToken token) {
        return ResponseEntity.ok(typeMediaService.getAllMedia(token, pageable));
    }
}

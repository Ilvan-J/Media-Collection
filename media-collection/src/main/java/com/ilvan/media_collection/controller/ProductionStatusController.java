package com.ilvan.media_collection.controller;

import com.ilvan.media_collection.controller.dto.response.ProductionStatusResponseDto;
import com.ilvan.media_collection.services.ProductionStatusService;
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
@RequestMapping("/api/media-collection/production-status")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductionStatusController {

    private final ProductionStatusService productionStatusService;

    public ProductionStatusController(ProductionStatusService productionStatusService) {
        this.productionStatusService = productionStatusService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ProductionStatusResponseDto>> getAllProductionStatus(@PageableDefault(
            size = 10,
            sort = "name",
            direction = Sort.Direction.ASC
    )Pageable pageable,
                                                                                    JwtAuthenticationToken token) {
        return ResponseEntity.ok(productionStatusService.getAllProductionStatus(pageable));
    }
}

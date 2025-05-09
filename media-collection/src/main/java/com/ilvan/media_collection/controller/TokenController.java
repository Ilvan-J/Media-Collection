package com.ilvan.media_collection.controller;

import org.springframework.http.ResponseEntity;

import com.ilvan.media_collection.controller.dto.LoginRequest;
import com.ilvan.media_collection.controller.dto.LoginResponse;
import com.ilvan.media_collection.services.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media-collection/token")
public class TokenController {

    private final TokenService tokenService;
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = tokenService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

}

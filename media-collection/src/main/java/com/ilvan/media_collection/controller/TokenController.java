package com.ilvan.media_collection.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ilvan.media_collection.controller.dto.LoginRequest;
import com.ilvan.media_collection.controller.dto.LoginResponse;
import com.ilvan.media_collection.services.TokenService;

@RestController
@RequestMapping("/api/media-collection")
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

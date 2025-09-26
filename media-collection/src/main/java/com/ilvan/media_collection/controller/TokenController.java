package com.ilvan.media_collection.controller;

import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        tokenService.login(loginRequest, response);
        return ResponseEntity.ok().build();
    }

}

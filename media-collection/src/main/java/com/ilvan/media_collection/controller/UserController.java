package com.ilvan.media_collection.controller;


import com.ilvan.media_collection.controller.dto.CreateUserDto;
import com.ilvan.media_collection.controller.dto.request.UserUpdateRequest;
import com.ilvan.media_collection.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media-collection/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/newUser")
    public ResponseEntity<Void> newUser (@RequestBody CreateUserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody UserUpdateRequest dto, JwtAuthenticationToken token) {
        userService.updatePassword(dto, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

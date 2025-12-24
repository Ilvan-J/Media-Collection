package com.ilvan.media_collection.services;


import com.ilvan.media_collection.controller.dto.CreateUserDto;
import com.ilvan.media_collection.controller.dto.LoginRequest;
import com.ilvan.media_collection.controller.dto.request.UserUpdateRequest;
import com.ilvan.media_collection.controller.erros.CustomGenericException;
import com.ilvan.media_collection.entities.Role;
import com.ilvan.media_collection.entities.User;
import com.ilvan.media_collection.repositories.RoleRepository;
import com.ilvan.media_collection.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserDto dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByEmail(dto.email());

        if (userFromDb.isPresent()) {
            throw new CustomGenericException("Email alredy in use", HttpStatus.CONFLICT);
        }

        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
    }

    public void updatePassword(UserUpdateRequest dto, JwtAuthenticationToken token) {
        var userUpdate = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CustomGenericException("User not found!", HttpStatus.NOT_FOUND));

        LoginRequest loginRequest = new LoginRequest(dto.email(), dto.currentPassword());

        if (!userUpdate.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new CustomGenericException("Current password invalid!", HttpStatus.UNAUTHORIZED);
        }

        var user = new User();
        user.setUserId(userUpdate.getUserId());
        user.setEmail(userUpdate.getEmail());
        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        user.setRoles(userUpdate.getRoles());

        userRepository.save(user);
    }
}

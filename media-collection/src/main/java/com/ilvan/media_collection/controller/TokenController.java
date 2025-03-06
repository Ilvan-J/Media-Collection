package com.ilvan.media_collection.controller;

import com.ilvan.media_collection.controller.dto.LoginRequest;
import com.ilvan.media_collection.controller.dto.LoginResponse;
import com.ilvan.media_collection.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {

       var user = userRepository.findByUserName(loginRequest.userName());

       if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
           System.out.println("Passei aqui ou seja deu errado.");
           throw new BadCredentialsException("userName or password is invalid!");
       }

       var now = Instant.now();
       var expiresIn = 300L;

       var claims = JwtClaimsSet.builder()
               .issuer("mybackend")
               .subject(user.get().getUserId().toString())
               .issuedAt(now)
               .expiresAt(now.plusSeconds(expiresIn))
               .build();

       var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

}

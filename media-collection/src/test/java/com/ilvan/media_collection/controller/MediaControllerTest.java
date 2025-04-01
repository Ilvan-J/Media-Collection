package com.ilvan.media_collection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilvan.media_collection.controller.dto.request.MediaRequestDto;
import com.ilvan.media_collection.services.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MediaControllerTest {

    private final static String API_URL = "/api/media-collection/medias";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private MediaService mediaService;

    @InjectMocks
    private MediaController mediaController;

    @Autowired
    private ObjectMapper objectMapper;

    private MediaRequestDto mediaRequestDto;

    @BeforeEach
    void setUp() {
        mediaRequestDto = new MediaRequestDto("Teste", 1, 1L, 1L, 1L);
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve salvar uma mídia com sucesso")
    void newMedia() throws Exception{
        Mockito.doNothing().when(mediaService).saveMedia(Mockito.any(MediaRequestDto.class), Mockito.any(JwtAuthenticationToken.class));

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", UUID.randomUUID().toString());
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "H256")
                .claims(existingClaims -> existingClaims.putAll(claims))
                .build();

        var authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var authentication = new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("sub"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mediaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
    
}
package com.ilvan.media_collection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilvan.media_collection.controller.dto.request.MediaRequestDto;
import com.ilvan.media_collection.controller.dto.response.MediaResponseDto;
import com.ilvan.media_collection.controller.erros.CustomGenericException;
import com.ilvan.media_collection.services.MediaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.*;


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

    private static Jwt getMockJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "h33fds-h2f5N9-jeHx2qj-NWvazCPd3");
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "H256")
                .claims(existingClaims -> existingClaims.putAll(claims))
                .build();
        return jwt;
    }

    private static JwtAuthenticationToken getMockToken(Jwt jwt) {
        var authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var authentication = new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("sub"));
        return authentication;
    }

    @Test
    @DisplayName("Deve salvar uma mídia com sucesso")
    void newMediaCreatedIsOkTest() throws Exception{
        Mockito.doNothing().when(mediaService).saveMedia(Mockito.any(MediaRequestDto.class), Mockito.any(JwtAuthenticationToken.class));

        var jwt = getMockJwt();

        JwtAuthenticationToken authentication = getMockToken(jwt);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mediaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Deve retornar 404 se o serviço lançar CustomGenericException.")
    void newMediaShouldReturnNotFoundWhenServiceThrowsCustomGenericExceptionTest () throws Exception {
        var jwt = getMockJwt();
        var authentication = getMockToken(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Mockito.doThrow(new CustomGenericException("Resource not found", HttpStatus.NOT_FOUND))
                .when(mediaService).saveMedia(Mockito.any(MediaRequestDto.class), Mockito.any(JwtAuthenticationToken.class));


        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mediaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar um page de mídias")
    void listAllMediaOfLoggedInUserTest() throws Exception {
        var jwt = getMockJwt();
        var authenticationToken = getMockToken(jwt);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        var mediaResponseDto = new MediaResponseDto(
                UUID.randomUUID(),
                "Test Media",
                5,
                "Anime",
                "Finalizado",
                "Assistido",
                "admin@gmail.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        var mediaPage = new PageImpl<>(List.of(mediaResponseDto), PageRequest.of(0, 10), 1);

        BDDMockito.when(mediaService.findWithFilterAndSorting(
                Mockito.eq(null),             // typeMedia (vem como null se não fornecido)
                Mockito.eq(null),             // productionStatus (vem como null se não fornecido)
                Mockito.eq(null),             // watchingStatus (vem como null se não fornecido)
                Mockito.eq(null),             // name (vem como null se não fornecido)
                Mockito.eq("dateOfAdded"),    // orderBy (valor padrão do Controller)
                Mockito.eq("desc"),           // orderDirection (valor padrão do Controller)
                Mockito.any(PageRequest.class), // page e pageSize são capturados pelo PageRequest
                Mockito.any(JwtAuthenticationToken.class)
        )).thenReturn(new PageImpl<>(List.of(mediaResponseDto)));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/list-all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", Matchers.is(mediaResponseDto.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].seasons", Matchers.is(mediaResponseDto.seasons())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nameTypeMedia", Matchers.is(mediaResponseDto.nameTypeMedia())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nameProductionStatus", Matchers.is(mediaResponseDto.nameProductionStatus())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nameWatchingStatus", Matchers.is(mediaResponseDto.nameWatchingStatus())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userEmail", Matchers.is(mediaResponseDto.userEmail())))
                .andDo(MockMvcResultHandlers.print());

    }
    
}
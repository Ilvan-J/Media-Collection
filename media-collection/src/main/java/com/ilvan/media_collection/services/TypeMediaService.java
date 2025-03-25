package com.ilvan.media_collection.services;

import com.ilvan.media_collection.controller.dto.TypeMediaResponseDTO;
import com.ilvan.media_collection.repositories.TypeMediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeMediaService {

    private final TypeMediaRepository typeMediaRepository;
    private final ModelMapper modelMapper;

    public TypeMediaService(TypeMediaRepository typeMediaRepository, ModelMapper modelMapper) {
        this.typeMediaRepository = typeMediaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Page<TypeMediaResponseDTO> getAllMedia(JwtAuthenticationToken token, Pageable pageable) {
        var typeMediaList = typeMediaRepository.findAll();
        var typeMediaPage = new PageImpl<>(typeMediaList, pageable, pageable.getPageSize());
        return typeMediaPage.map(typeMedia -> new TypeMediaResponseDTO(
                typeMedia.getIdTypeMedia(),
                typeMedia.getName()
        ));
    }

}

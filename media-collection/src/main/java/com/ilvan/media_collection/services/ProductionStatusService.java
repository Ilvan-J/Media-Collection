package com.ilvan.media_collection.services;


import com.ilvan.media_collection.controller.dto.response.ProductionStatusResponseDto;
import com.ilvan.media_collection.repositories.ProductionStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductionStatusService {

    private final ProductionStatusRepository productionStatusRepository;

    public ProductionStatusService(ProductionStatusRepository productionStatusRepository) {
        this.productionStatusRepository = productionStatusRepository;
    }

    @Transactional
    public Page<ProductionStatusResponseDto> getAllProductionStatus(Pageable pageable) {
        var productionStatusList = productionStatusRepository.findAll();
        var productionStatusPage = new PageImpl<>(productionStatusList, pageable, pageable.getPageSize());

        return productionStatusPage.map(productionStatus -> new ProductionStatusResponseDto(
                productionStatus.getIdProductionStatus(),
                productionStatus.getName()
        ));
    }
}

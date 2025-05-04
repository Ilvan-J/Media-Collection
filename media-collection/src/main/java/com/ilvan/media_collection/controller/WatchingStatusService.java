package com.ilvan.media_collection.controller;


import com.ilvan.media_collection.controller.dto.response.WatchingStatusResponseDto;
import com.ilvan.media_collection.repositories.WatchingStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WatchingStatusService {

    private final WatchingStatusRepository watchingStatusRepository;

    public WatchingStatusService(WatchingStatusRepository watchingStatusRepository) {
        this.watchingStatusRepository = watchingStatusRepository;
    }

    public Page<WatchingStatusResponseDto> getAllWatchingStatus(Pageable pageable) {
        var watchinStatus = watchingStatusRepository.findAll(pageable);
        return watchinStatus.map(watchingStatus -> new WatchingStatusResponseDto(
                watchingStatus.getIdWatchingStatus(),
                watchingStatus.getName()
        ));
    }
}

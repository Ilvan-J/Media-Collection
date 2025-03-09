package com.ilvan.media_collection.repositories;

import com.ilvan.media_collection.entities.WatchingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchingStatusRepository extends JpaRepository<WatchingStatus, Long> {
}

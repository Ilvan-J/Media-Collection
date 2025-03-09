package com.ilvan.media_collection.repositories;

import com.ilvan.media_collection.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}

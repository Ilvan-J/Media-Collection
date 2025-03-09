package com.ilvan.media_collection.repositories;

import com.ilvan.media_collection.entities.TypeMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeMediaRepository extends JpaRepository<TypeMedia, Long> {
}

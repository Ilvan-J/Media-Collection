package com.ilvan.media_collection.repositories;

import com.ilvan.media_collection.entities.Media;
import com.ilvan.media_collection.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    @Query("SELECT m FROM Media m " +
            "JOIN FETCH m.typeMedia " +
            "JOIN FETCH m.productionStatus " +
            "JOIN FETCH m.watchingStatus " +
            "JOIN FETCH m.user " +
            "WHERE m.user = :user ")
    Page<Media>findByUser(Pageable pageable, @Param("user") User user);
}

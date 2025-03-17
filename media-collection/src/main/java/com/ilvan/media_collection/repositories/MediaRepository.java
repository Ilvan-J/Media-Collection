package com.ilvan.media_collection.repositories;

import com.ilvan.media_collection.entities.*;
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
           "WHERE m.user = :user " +
           "AND (:typeMedia IS NULL OR m.typeMedia = :typeMedia) " +
           "AND (:productionStatus IS NULL OR m.productionStatus = :productionStatus) " +
           "AND (:watchingStatus IS NULL OR m.watchingStatus = :watchingStatus) " +
           "AND (:name IS NULL OR LOWER (m.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
    )
    Page<Media> findByUserWithFilters(
            @Param("user") User user,
            @Param("typeMedia")TypeMedia typeMedia,
            @Param("productionStatus")ProductionStatus productionStatus,
            @Param("watchingStatus")WatchingStatus watchingStatus,
            @Param("name") String name,
            Pageable pageable
            );
}

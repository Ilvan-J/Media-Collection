package com.ilvan.media_collection.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_media")
    private UUID idMedia;

    private String name;

    private Integer seasons;

    @ManyToOne
    @JoinColumn(name = "id_type_media")
    private TypeMedia typeMedia;

    @ManyToOne
    @JoinColumn(name = "id_production_status")
    private ProductionStatus productionStatus;

    @ManyToOne
    @JoinColumn(name = "id_watching_status")
    private WatchingStatus watchingStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateOfAdded;

    @UpdateTimestamp
    private LocalDateTime modificationDate;

    public Media() {
    }

    public Media(UUID idMedia,
                 String name,
                 Integer seasons,
                 TypeMedia typeMedia,
                 ProductionStatus productionStatus,
                 WatchingStatus watchingStatus,
                 User user) {
        this.idMedia = idMedia;
        this.name = name;
        this.seasons = seasons;
        this.typeMedia = typeMedia;
        this.productionStatus = productionStatus;
        this.watchingStatus = watchingStatus;
        this.user = user;
    }

    public UUID getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(UUID idMedia) {
        this.idMedia = idMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public TypeMedia getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(TypeMedia typeMedia) {
        this.typeMedia = typeMedia;
    }

    public ProductionStatus getProductionStatus() {
        return productionStatus;
    }

    public void setProductionStatus(ProductionStatus productionStatus) {
        this.productionStatus = productionStatus;
    }

    public WatchingStatus getWatchingStatus() {
        return watchingStatus;
    }

    public void setWatchingStatus(WatchingStatus watchingStatus) {
        this.watchingStatus = watchingStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateOfAdded() {
        return dateOfAdded;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }
}

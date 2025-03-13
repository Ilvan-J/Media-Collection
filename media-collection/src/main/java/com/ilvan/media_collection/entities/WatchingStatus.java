package com.ilvan.media_collection.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_watching_status")
public class WatchingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_watching_status")
    private Long idWatchingStatus;
    private String name;

    public WatchingStatus() {
    }

    public WatchingStatus(Long idWatchingStatus, String name) {
        this.idWatchingStatus = idWatchingStatus;
        this.name = name;
    }

    public Long getIdWatchingStatus() {
        return idWatchingStatus;
    }

    public void setIdWatchingStatus(Long idWatchingStatus) {
        this.idWatchingStatus = idWatchingStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

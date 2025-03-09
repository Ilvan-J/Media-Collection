package com.ilvan.media_collection.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_media")
    private UUID idMedia;
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_type_media")
    private TypeMedia typeMedia;
    private Integer seasons;

    @ManyToOne
    @JoinColumn(name = "id_productionStatus")
    private ProductionStatus productionStatus;

    @ManyToOne
    @JoinColumn(name = "id_watching_status")
    private WatchingStatus watchingStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime DateOfAdded;

    @UpdateTimestamp
    private LocalDateTime ModificationDate;

}

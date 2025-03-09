package com.ilvan.media_collection.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_watching_status")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WatchingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_watching_status")
    private Long idWatchingStatus;
    private String name;
}

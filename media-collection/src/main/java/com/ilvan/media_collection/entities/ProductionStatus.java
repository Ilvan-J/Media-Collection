package com.ilvan.media_collection.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_productions_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_productionStatus")
    private Long idProductionStatus;
    private String name;
}

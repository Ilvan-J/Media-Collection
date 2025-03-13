package com.ilvan.media_collection.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_productions_status")
public class ProductionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_productionStatus")
    private Long idProductionStatus;
    private String name;

    public ProductionStatus() {
    }

    public ProductionStatus(Long idProductionStatus, String name) {
        this.idProductionStatus = idProductionStatus;
        this.name = name;
    }

    public Long getIdProductionStatus() {
        return idProductionStatus;
    }

    public void setIdProductionStatus(Long idProductionStatus) {
        this.idProductionStatus = idProductionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

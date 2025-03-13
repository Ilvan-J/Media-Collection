package com.ilvan.media_collection.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_type_media")
public class TypeMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_media")
    private Long idTypeMedia;

    private String name;

    public TypeMedia() {
    }

    public TypeMedia(Long idTypeMedia, String name) {
        this.idTypeMedia = idTypeMedia;
        this.name = name;
    }

    public Long getIdTypeMedia() {
        return idTypeMedia;
    }

    public void setIdTypeMedia(Long idTypeMedia) {
        this.idTypeMedia = idTypeMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

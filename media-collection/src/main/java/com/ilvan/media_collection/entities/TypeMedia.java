package com.ilvan.media_collection.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_type_media")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_media")
    private Long idTypeMedia;

    private String name;

}

package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Region {

    @Id
    @GeneratedValue
    private UUID id;

    private String regionName;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private Set<Court> courts;


}

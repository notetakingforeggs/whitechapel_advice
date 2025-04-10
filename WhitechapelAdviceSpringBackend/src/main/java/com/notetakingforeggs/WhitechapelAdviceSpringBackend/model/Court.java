package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Court {
    @Id
    private long id;

//    private String name; // not sure about getting the names of different courts... worthwhile? idk
    private String city;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL)
    private Set<CourtCase> courtCases;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id")
    private Region region;



}

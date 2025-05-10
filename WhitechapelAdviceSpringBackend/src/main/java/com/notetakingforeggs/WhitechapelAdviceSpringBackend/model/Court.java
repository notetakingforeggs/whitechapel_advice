package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL)
    private Set<CourtCase> courtCases;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id")
    private Region region;



}

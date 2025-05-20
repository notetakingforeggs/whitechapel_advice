package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private List<String> alertTerm;

    private Long lastNotifiedTimestamp;
}

package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    Long id;

    String type;

    List<String> alertTerm;

    long lastNotifiedTimestamp;
}

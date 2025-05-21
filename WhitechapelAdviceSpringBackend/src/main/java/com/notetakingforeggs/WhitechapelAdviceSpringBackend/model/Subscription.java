package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;

    private Long chatId;

    private List<String> alertTermsClaimant;
    private List<String> alertTermsDefendant;

    private Long lastNotifiedTimestamp;
}

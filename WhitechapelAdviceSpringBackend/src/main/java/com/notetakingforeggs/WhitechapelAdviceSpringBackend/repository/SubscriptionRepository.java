package com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByChatId(Long id);
}

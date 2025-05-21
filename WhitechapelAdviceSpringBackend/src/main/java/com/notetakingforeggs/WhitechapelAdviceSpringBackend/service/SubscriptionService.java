package com.notetakingforeggs.WhitechapelAdviceSpringBackend.service;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot.TelegramBot;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Subscription;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final TelegramBot bot;

    public void addOrUpdateClaimant(Long chatId, String claimaint){
        Subscription subscription = repository.findByChatId(chatId);
        if(subscription == null){
            subscription = new Subscription();
        }
        subscription.setChatId(chatId);
        subscription.getAlertTermsClaimant().add(claimaint);

        subscription.setLastNotifiedTimestamp(Instant.now().getEpochSecond());

        bot.sendMessage(chatId.toString(), "A new alert subscription has been added with claimant as " + claimaint);
    }
    public void addOrUpdateDefendant(Long chatId, String defendant){
        Subscription subscription = repository.findByChatId(chatId);
        if(subscription == null){
            subscription = new Subscription();
        }
        subscription.setChatId(chatId);
        subscription.getAlertTermsDefendant().add(defendant);

        subscription.setLastNotifiedTimestamp(Instant.now().getEpochSecond());

        bot.sendMessage(chatId.toString(), "A new alert subscription has been added with defendant as " + defendant );
    }
}

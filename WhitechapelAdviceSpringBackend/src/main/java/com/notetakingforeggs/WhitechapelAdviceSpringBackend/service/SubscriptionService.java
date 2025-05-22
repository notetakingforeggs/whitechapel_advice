package com.notetakingforeggs.WhitechapelAdviceSpringBackend.service;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot.TelegramBot;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Subscription;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
//@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;

    @Lazy
    private final TelegramBot bot;

    public SubscriptionService(SubscriptionRepository repository, @Lazy TelegramBot bot) {
        this.repository = repository;
        this.bot = bot;
    }

    public void addOrUpdateClaimant(Long chatId, String claimaint){
        System.out.println("addOrUpdating Claimaint");
        Subscription subscription = repository.findByChatId(chatId);
        if(subscription == null){
            subscription = new Subscription();
        }
        subscription.setChatId(chatId);
        subscription.getAlertTermsClaimant().add(claimaint);
        subscription.setLastNotifiedTimestamp(Instant.now().getEpochSecond());

        Subscription sub = repository.save(subscription);
        System.out.println(sub);

        bot.sendMessage(chatId.toString(), "A new alert subscription has been added with claimant as " + claimaint);
    }
    public void addOrUpdateDefendant(Long chatId, String defendant){
        System.out.println("Adding or updating defendant");
        Subscription subscription = repository.findByChatId(chatId);
        if(subscription == null){
            subscription = new Subscription();
        }
        subscription.setChatId(chatId);
        subscription.getAlertTermsDefendant().add(defendant);

        subscription.setLastNotifiedTimestamp(Instant.now().getEpochSecond());
        Subscription sub = repository.save(subscription);
        System.out.println(sub);

        bot.sendMessage(chatId.toString(), "A new alert subscription has been added with defendant as " + defendant );
    }

    public Subscription getSub (Long chatId){
        return repository.findByChatId(chatId);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
    public void flush(){
        repository.flush();
    }
}

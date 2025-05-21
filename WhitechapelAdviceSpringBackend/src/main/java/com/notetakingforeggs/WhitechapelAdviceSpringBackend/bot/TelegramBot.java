package com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;




@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String username;

    public TelegramBot(@Value("${bot.token}") String token, @Value("${bot.username}") String username) {
        super(token);
        this.username = username;

    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText().trim().toLowerCase();

            if(messageText.trim().startsWith("/claimaint:")){

                sendMessage(chatId, "you said claimant");
            } else if (messageText.trim().startsWith("/defendant:")) {
                sendMessage(chatId, "you said defendant");
            } else if (messageText.trim().startsWith("/view")) {
                sendMessage(chatId, "listing all your subscriptions:");
            } else{
                String helpText = """ 
                        Welcome to the advice service Telegram Bot!
                        to sign up for alerts based on claimaint, type /claimaint: followed by the name or names of the claimant you wish to be alerted for, eg "claimaint: joe blogs". To sign up for alerts based on names of defendants send a message like defendant:nameofdefendant.
                        """;
                sendMessage(chatId, helpText);
            }
        }

    }

    public void sendMessage(String chatId, String text){
        SendMessage message = new SendMessage(chatId, text);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

}

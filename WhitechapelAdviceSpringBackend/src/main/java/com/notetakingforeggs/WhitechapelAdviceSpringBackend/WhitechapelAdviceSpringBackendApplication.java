package com.notetakingforeggs.WhitechapelAdviceSpringBackend;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class WhitechapelAdviceSpringBackendApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(WhitechapelAdviceSpringBackendApplication.class, args);

		try{
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(context.getBean(TelegramBot.class));
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

}

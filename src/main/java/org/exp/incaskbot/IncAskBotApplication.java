package org.exp.incaskbot;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IncAskBotApplication {

    @Value("${telegram.bot.token}")
    private String token;

    public static void main(String[] args) {
        SpringApplication.run(IncAskBotApplication.class, args);
    }

    @Bean
    public TelegramBot botConfig() {
        return new TelegramBot(token);
    }
}

package ru.konin.botik.java_t_bot.configuration;
import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBot telegramBot(@Value("${telegram.bot.token}") String token) {
        return new TelegramBot(token);
    }
}

package io.github.q7nm.nasa_telegram_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import io.github.q7nm.nasa_telegram_bot.router.UpdateRouterService;

@Component
public class NasaBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String token;
    private final UpdateRouterService updateRouterService;

    @Autowired
    public NasaBot(@Value("${telegram.bot.token}") String token, UpdateRouterService updateRouterService) {
        this.token = token;
        this.updateRouterService = updateRouterService;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            updateRouterService.route(update);
        }
    }

}
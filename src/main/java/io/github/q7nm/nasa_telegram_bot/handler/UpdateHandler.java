package io.github.q7nm.nasa_telegram_bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {

    public boolean supports(Update update);

    public void handle(Update update);
}
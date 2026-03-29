package io.github.q7nm.nasa_telegram_bot.router;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import io.github.q7nm.nasa_telegram_bot.handler.UpdateHandler;

@Service
public class UpdateRouterService {

    private final List<UpdateHandler> handlers;

    public UpdateRouterService(List<UpdateHandler> handlers) {
        this.handlers = handlers;
    }

    public void route(Update update) {
        for(UpdateHandler handler : handlers) {
            if (handler.supports(update)) {
                handler.handle(update);
            }
        }
    }
    
}
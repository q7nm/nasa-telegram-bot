package io.github.q7nm.nasa_telegram_bot.handler.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import io.github.q7nm.nasa_telegram_bot.entity.User;
import io.github.q7nm.nasa_telegram_bot.entity.UserRepository;
import io.github.q7nm.nasa_telegram_bot.handler.UpdateHandler;

@Component
public class SubscribeToApodCommandHandler implements UpdateHandler {

    private final TelegramClient telegramClient;
    private final UserRepository userRepository;

    @Autowired
    public SubscribeToApodCommandHandler(TelegramClient telegramClient, UserRepository userRepository) {
        this.telegramClient = telegramClient;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Update update) {
        return update != null && update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().trim().equalsIgnoreCase("/subscribe_to_apod");
    }

    @Override
    public void handle(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = userRepository.findByTelegramId(telegramId).orElseGet(() -> {
            User newUser = new User();
            newUser.setTelegramId(telegramId);
            return newUser;
        });

        user.setSubscribedToApod(true);
        userRepository.save(user);

        try {
            telegramClient.execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("You have successfully subscribed to the daily APOD!")
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

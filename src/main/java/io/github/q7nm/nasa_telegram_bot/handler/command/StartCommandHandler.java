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
public class StartCommandHandler implements UpdateHandler {

    private final TelegramClient telegramClient;
    private final UserRepository userRepository;

    @Autowired
    public StartCommandHandler(TelegramClient telegramClient, UserRepository userRepository) {
        this.telegramClient = telegramClient;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Update update) {
        return update != null && update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().trim().equalsIgnoreCase("/start");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long telegramId = update.getMessage().getFrom().getId();

        userRepository.findByTelegramId(telegramId).orElseGet(() -> {
            User user = new User();
            user.setTelegramId(telegramId);
            return userRepository.save(user);
        });

        String welcomeMessage = "Hi! I'm your space bot powered by NASA API. Check out the menu to see all available commands.";

        try {
            telegramClient.execute(SendMessage.builder().chatId(chatId).text(welcomeMessage).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

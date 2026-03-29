package io.github.q7nm.nasa_telegram_bot.handler.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import io.github.q7nm.nasa_telegram_bot.entity.dto.ApodDTO;
import io.github.q7nm.nasa_telegram_bot.handler.UpdateHandler;
import io.github.q7nm.nasa_telegram_bot.service.nasa.NasaService;

@Component
public class ApodCommandHandler implements UpdateHandler {

    private final TelegramClient telegramClient;
    private final NasaService nasaService;

    @Autowired
    public ApodCommandHandler(TelegramClient telegramClient, NasaService nasaService) {
        this.telegramClient = telegramClient;
        this.nasaService = nasaService;
    }

    @Override
    public boolean supports(Update update) {
        return update != null && update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().trim().equalsIgnoreCase("/apod");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        ApodDTO apod = nasaService.getApod();

        try {
            sendApod(chatId, apod);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendApod(Long chatId, ApodDTO apod) throws TelegramApiException {
        if ("image".equalsIgnoreCase(apod.mediaType())) {
            telegramClient.execute(SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(apod.url()))
                    .caption(apod.title() + "\n\n" + apod.explanation() + "\n\n" + apod.copyright())
                    .build());
        } else {
            telegramClient.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(apod.title() + "\n\n" + apod.explanation() + "\n\n" + apod.copyright()
                            + "\n\n" + apod.url())
                    .build());
        }
    }

}

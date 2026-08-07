package io.github.q7nm.nasa_telegram_bot.handler.command;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import io.github.q7nm.nasa_telegram_bot.entity.dto.neo.AsteroidDTO;
import io.github.q7nm.nasa_telegram_bot.entity.dto.neo.CloseApproachDTO;
import io.github.q7nm.nasa_telegram_bot.entity.dto.neo.NasaNeoFeedDTO;
import io.github.q7nm.nasa_telegram_bot.handler.UpdateHandler;
import io.github.q7nm.nasa_telegram_bot.service.nasa.NasaService;

@Component
public class NeoCommandHandler implements UpdateHandler {

    private final TelegramClient telegramClient;
    private final NasaService nasaService;

    @Autowired
    public NeoCommandHandler(TelegramClient telegramClient, NasaService nasaService) {
        this.telegramClient = telegramClient;
        this.nasaService = nasaService;
    }

    @Override
    public boolean supports(Update update) {
        return update != null && update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().trim().equalsIgnoreCase("/neo");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        NasaNeoFeedDTO neo = nasaService.getNeoFeed(LocalDate.now(), LocalDate.now().plusDays(1));

        try {
            sendNeo(chatId, neo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNeo(Long chatId, NasaNeoFeedDTO neo) throws TelegramApiException {
        StringBuilder message = new StringBuilder();

        message.append("☄️ Near Earth Objects\n\n");
        message.append("Total objects: ")
                .append(neo.elementCount())
                .append("\n\n");

        List<AsteroidDTO> hazardousAsteroids = neo.nearEarthObjects()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(AsteroidDTO::potentiallyHazardous)
                .limit(5)
                .toList();

        message.append("⚠️ Hazardous asteroids:\n");

        if (hazardousAsteroids.isEmpty()) {
            message.append("Hazardous not found\n");
        } else {
            hazardousAsteroids.forEach(asteroid -> {
                message.append("🌑 ")
                        .append(asteroid.name())
                        .append("\n");
                        
                message.append("🔗 Info: ")
                        .append(asteroid.nasaJplUrl())
                        .append("\n");

                if (!asteroid.closeApproachData().isEmpty()) {
                    CloseApproachDTO approach = asteroid.closeApproachData().get(0);

                    message.append("📅 Date: ")
                            .append(approach.closeApproachDate())
                            .append("\n");

                    message.append("🌍 Distance: ")
                            .append(approach.missDistance().kilometers())
                            .append(" km\n");

                    message.append("🚀 Velocity: ")
                            .append(approach.relativeVelocity().kilometersPerHour())
                            .append(" km/h\n\n");
                }
            });
        }

        telegramClient.execute(
                SendMessage.builder()
                        .chatId(chatId)
                        .text(message.toString())
                        .build());
    }
}

package io.github.q7nm.nasa_telegram_bot.scheduler;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.github.q7nm.nasa_telegram_bot.entity.User;
import io.github.q7nm.nasa_telegram_bot.entity.UserRepository;
import io.github.q7nm.nasa_telegram_bot.entity.dto.neo.NasaNeoFeedDTO;
import io.github.q7nm.nasa_telegram_bot.handler.command.NeoCommandHandler;
import io.github.q7nm.nasa_telegram_bot.service.nasa.NasaService;

@Component
public class NeoScheduler {

    private final UserRepository userRepository;
    private final NasaService nasaService;
    private final NeoCommandHandler neoCommandHandler;

    @Autowired
    public NeoScheduler(UserRepository userRepository, NasaService nasaService, NeoCommandHandler neoCommandHandler) {
        this.userRepository = userRepository;
        this.nasaService = nasaService;
        this.neoCommandHandler = neoCommandHandler;
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "UTC")
    public void sendDailyNeo() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = today.minusDays(1);

        NasaNeoFeedDTO neo = nasaService.getNeoFeed(startDate, today);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.isSubscribedToNeo()) {
                try {
                    neoCommandHandler.sendNeo(user.getTelegramId(), neo);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

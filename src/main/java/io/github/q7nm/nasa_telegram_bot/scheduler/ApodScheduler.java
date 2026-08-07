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
import io.github.q7nm.nasa_telegram_bot.entity.dto.ApodDTO;
import io.github.q7nm.nasa_telegram_bot.handler.command.ApodCommandHandler;
import io.github.q7nm.nasa_telegram_bot.service.nasa.NasaService;

@Component
public class ApodScheduler {

    private final UserRepository userRepository;
    private final NasaService nasaService;
    private final ApodCommandHandler apodCommandHandler;

    @Autowired
    public ApodScheduler(UserRepository userRepository, NasaService nasaService, ApodCommandHandler apodCommandHandler) {
        this.userRepository = userRepository;
        this.nasaService = nasaService;
        this.apodCommandHandler = apodCommandHandler;
    }

    @Scheduled(cron = "0 0 6 * * *", zone = "UTC")
    public void sendDailyApod() {
        ApodDTO apod = nasaService.getApod(LocalDate.now(ZoneOffset.UTC));

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.isSubscribedToApod()) {
                try {
                    apodCommandHandler.sendApod(user.getTelegramId(), apod);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

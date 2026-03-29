package io.github.q7nm.nasa_telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class NasaTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasaTelegramBotApplication.class, args);
	}

}

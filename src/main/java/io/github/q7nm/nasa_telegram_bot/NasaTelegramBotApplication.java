package io.github.q7nm.nasa_telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NasaTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasaTelegramBotApplication.class, args);
	}

}

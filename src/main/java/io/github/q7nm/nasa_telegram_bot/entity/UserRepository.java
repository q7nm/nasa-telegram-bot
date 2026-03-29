package io.github.q7nm.nasa_telegram_bot.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByTelegramId(Long telegramId);

    List<User> findAllByOrderByCreatedAtDesc();
}

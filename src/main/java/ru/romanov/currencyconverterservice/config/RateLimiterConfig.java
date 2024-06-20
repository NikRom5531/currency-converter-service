package ru.romanov.currencyconverterservice.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Конфигурационный класс для настройки Rate Limiter в приложении.
 * <p>
 * Этот класс определяет бин {@link Bucket}, который представляет собой ограничитель (Rate Limiter),
 * настроенный на заданное количество запросов (RATE_LIMIT) за определенный период времени (TIME_LIMIT_PER_SECONDS).
 * Используется библиотека Bucket4j для реализации Rate Limiter.
 */
@Configuration
public class RateLimiterConfig {
    private final int RATE_LIMIT = 5;               // Количество попыток
    private final int TIME_LIMIT_PER_SECONDS = 1;   // Время в секундах

    /**
     * Создает и возвращает бин {@link Bucket} для Rate Limiter.
     * <p>
     * Ограничитель настроен на RATE_LIMIT попыток за TIME_LIMIT_PER_SECONDS секунд.
     *
     * @return экземпляр {@link Bucket} для Rate Limiter.
     */
    @Bean
    public Bucket bucket() {
        Refill refill = Refill.greedy(RATE_LIMIT, Duration.ofSeconds(TIME_LIMIT_PER_SECONDS));
        Bandwidth limit = Bandwidth.classic(RATE_LIMIT, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}

package ru.romanov.currencyconverterservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Конфигурационный класс для хранения кодов валют.
 * <p>
 * Этот класс используется для загрузки и хранения кодов валют из конфигурационных файлов
 * приложения. Аннотация {@code @ConfigurationProperties} указывает Spring Boot на то,
 * что свойства, начинающиеся с префикса {@code currencies}, должны быть загружены
 * в этот класс из конфигурационных файлов.
 * <p>
 * Аннотация {@code @Configuration} обозначает, что этот класс является конфигурационным
 * бином Spring.
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "currencies")
public class CurrencyConfig {
    /**
     * Коллекция для хранения кодов валют.
     * <p>
     * Каждая запись в коллекции представляет собой пару ключ-значение, где ключ -
     * это код валюты, а значение - название валюты.
     */
    private Map<String, String> codes;
}

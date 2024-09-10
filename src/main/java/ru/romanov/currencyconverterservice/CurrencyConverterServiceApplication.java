package ru.romanov.currencyconverterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;

/**
 * Главный класс приложения для сервиса конвертации валют.
 * <p>
 * Этот класс является точкой входа для запуска приложения Spring Boot. Он настраивает
 * приложение для работы с Feign-клиентами и загружает настройки валют из конфигурационных
 * свойств.
 */
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(CurrencyConfig.class)
public class CurrencyConverterServiceApplication {
    /**
     * Метод main, который запускает приложение Spring Boot.
     *
     * @param args Аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterServiceApplication.class, args);
    }
}

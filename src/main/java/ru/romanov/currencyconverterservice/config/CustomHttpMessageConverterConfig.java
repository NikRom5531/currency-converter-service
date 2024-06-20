package ru.romanov.currencyconverterservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурационный класс для настройки HTTP-конвертеров в приложении.
 * <p>
 * Этот класс содержит методы конфигурации для установки кастомного HTTP-конвертера
 * {@link MappingJackson2HttpMessageConverter} в {@link RestTemplate}.
 */
@Configuration
public class CustomHttpMessageConverterConfig {
    /**
     * Создает и возвращает экземпляр {@link RestTemplate}, настроенный с кастомным HTTP-конвертером.
     *
     * @return экземпляр {@link RestTemplate} с кастомным HTTP-конвертером.
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(customJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    /**
     * Создает и возвращает кастомный {@link MappingJackson2HttpMessageConverter}.
     * <p>
     * Конвертер настроен для поддержки типа медиа "application/javascript" с кодировкой UTF-8.
     *
     * @return кастомный {@link MappingJackson2HttpMessageConverter}.
     */
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(List.of(new MediaType("application", "javascript", StandardCharsets.UTF_8)));
        return jsonConverter;
    }
}

package ru.romanov.currencyconverterservice.config;

import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Конфигурационный класс для настройки Feign клиента в приложении.
 * <p>
 * Этот класс определяет методы конфигурации для настройки декодера {@link Decoder} Feign клиента
 * с использованием {@link ResponseEntityDecoder} и {@link SpringDecoder}.
 * Также настраивает кастомный HTTP-конвертер {@link MappingJackson2HttpMessageConverter}
 * для поддержки типа медиа "application/javascript" с кодировкой UTF-8.
 */
@Configuration
public class FeignConfig {
    /**
     * Создает и возвращает экземпляр {@link Decoder} для Feign клиента,
     * настроенный с {@link ResponseEntityDecoder} и {@link SpringDecoder}.
     *
     * @return экземпляр {@link Decoder} для Feign клиента.
     */
    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(feignJackson2HttpMessageConverter())));
    }

    /**
     * Создает и возвращает кастомный {@link MappingJackson2HttpMessageConverter} для Feign клиента.
     * <p>
     * Конвертер настроен для поддержки типа медиа "application/javascript" с кодировкой UTF-8.
     *
     * @return кастомный {@link MappingJackson2HttpMessageConverter} для Feign клиента.
     */
    @Bean
    public MappingJackson2HttpMessageConverter feignJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(List.of(new MediaType("application", "javascript", StandardCharsets.UTF_8)));
        return jsonConverter;
    }
}

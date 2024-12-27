package ru.romanov.currencyconverterservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String DAILY_CURRENCY_RATES = "dailyCurrencyRates";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(DAILY_CURRENCY_RATES);
    }
}

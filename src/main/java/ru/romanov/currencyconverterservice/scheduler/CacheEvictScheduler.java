package ru.romanov.currencyconverterservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.romanov.currencyconverterservice.config.CacheConfig;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class CacheEvictScheduler {

    private final CacheConfig cacheConfig;

    @Scheduled(cron = "0 0 0/6 * * ?")
    public void clearCache() {
        Cache dailyCurrencyRates = cacheConfig.cacheManager().getCache(CacheConfig.DAILY_CURRENCY_RATES);

        if (dailyCurrencyRates != null) {
            dailyCurrencyRates.clear();
            log.info("Cache '" + CacheConfig.DAILY_CURRENCY_RATES + "' cleared");
        }
    }
}

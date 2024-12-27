package ru.romanov.currencyconverterservice.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.romanov.currencyconverterservice.client.ExchangeRateClient;
import ru.romanov.currencyconverterservice.config.CacheConfig;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;

@Service
@RequiredArgsConstructor
public class CacheCurrencyConverterService {

    private final ExchangeRateClient exchangeRateClient;

    @Cacheable(value = CacheConfig.DAILY_CURRENCY_RATES)
    public CurrencyResponse getDailyCurrencyRates() {
        return exchangeRateClient.getDailyCurrencyRates();
    }
}

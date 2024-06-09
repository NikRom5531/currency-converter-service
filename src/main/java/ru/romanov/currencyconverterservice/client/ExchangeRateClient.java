package ru.romanov.currencyconverterservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;

@FeignClient(name = "exchange-rate-client", url = "${exchange.rate.service.url}")
public interface ExchangeRateClient {

    @GetMapping("/daily_json.js")
    CurrencyResponse getDailyCurrencyRates();
}

package ru.romanov.currencyconverterservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanov.currencyconverterservice.client.ExchangeRateClient;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;
import ru.romanov.currencyconverterservice.model.Currency;
import ru.romanov.currencyconverterservice.model.CurrencyConversion;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.repository.CurrencyConversionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyConverterService {
    private final ExchangeRateClient exchangeRateClient;
    private final CurrencyConfig currencyConfig;
    private final CurrencyConversionRepository currencyConversionRepository;

    public CurrencyResponse getDailyCurrencyRates() {
        return exchangeRateClient.getDailyCurrencyRates();
    }

    public double convert(String from, String to, double amount) {
        CurrencyResponse response = getDailyCurrencyRates();
        Currency fromCurrency = (from.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(from.toUpperCase()));
        Currency toCurrency = (to.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(to.toUpperCase()));

        if (fromCurrency == null || toCurrency == null) {
            return Double.NaN; //"Invalid currency code\n" + getSupportedCurrencies();
        }
        if (fromCurrency.getValue() == 0 || fromCurrency.getNominal() == 0 || toCurrency.getValue() == 0 || toCurrency.getNominal() == 0) {
            return Double.NaN; //"Invalid currency values";
        }
        double amountFrom = fromCurrency.getValue() / fromCurrency.getNominal();
        double amountTo = toCurrency.getValue() / toCurrency.getNominal();
        double rate = amountFrom / amountTo;
        double convertedAmount = amount * rate;
        saveConversion(from.toUpperCase(), amount, to.toUpperCase(), rate, convertedAmount);
        return convertedAmount; //"Amount " + amount + " from " + from + " to " + to + " converted " + convertedAmount;
    }

    public List<String> getSupportedCurrencies() {
        List<String> supportedCurrencies = new ArrayList<>();
        Map<String, String> supported = currencyConfig.getCodes();
        List<String> keys = supported.keySet().stream().toList();
        for (String key : keys) supportedCurrencies.add(key + " " + supported.get(key));
        return supportedCurrencies;
    }

    public void saveConversion(String fromCurrency, double amountBeforeConversion, String toCurrency, double conversionRate, double amountAfterConversion) {
        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setCurrencyFrom(fromCurrency);
        conversion.setCurrencyTo(toCurrency);
        conversion.setAmountBeforeConversion(amountBeforeConversion);
        conversion.setConversionRate(conversionRate);
        conversion.setAmountAfterConversion(amountAfterConversion);
        conversion.setConversionDate(LocalDateTime.now());
        currencyConversionRepository.save(conversion);
    }

    public Currency setRUB() {
        Currency currency = new Currency();
        currency.setNominal(1);
        currency.setValue(1);
        return currency;
    }
}

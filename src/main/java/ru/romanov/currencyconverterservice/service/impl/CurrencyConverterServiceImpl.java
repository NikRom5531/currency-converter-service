package ru.romanov.currencyconverterservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanov.currencyconverterservice.client.ExchangeRateClient;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;
import ru.romanov.currencyconverterservice.model.Currency;
import ru.romanov.currencyconverterservice.model.CurrencyConversion;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.repository.CurrencyConversionRepository;
import ru.romanov.currencyconverterservice.service.CurrencyConverterService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Реализация сервиса конвертации валют.
 * <p>
 * Этот класс предоставляет методы для получения текущих курсов валют, конвертации валют,
 * получения поддерживаемых валют и сохранения информации о конверсиях в базе данных.
 */
@Service
@AllArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    private final ExchangeRateClient exchangeRateClient;
    private final CurrencyConfig currencyConfig;
    private final CurrencyConversionRepository currencyConversionRepository;

    @Override
    public CurrencyResponse getDailyCurrencyRates() {
        return exchangeRateClient.getDailyCurrencyRates();
    }

    @Override
    public double convert(String from, String to, double amount) {
        if (!getSupportedCurrenciesCharCode().contains(from) || !getSupportedCurrenciesCharCode().contains(to))
            return Double.NaN;
        CurrencyResponse response = getDailyCurrencyRates();
        Currency fromCurrency = (from.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(from.toUpperCase()));
        Currency toCurrency = (to.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(to.toUpperCase()));
        if (fromCurrency == null || toCurrency == null) return Double.NaN;
        if (fromCurrency.getValue() == 0 || fromCurrency.getNominal() == 0 || toCurrency.getValue() == 0 || toCurrency.getNominal() == 0)
            return Double.NaN;
        double rate = (fromCurrency.getValue() / fromCurrency.getNominal()) / (toCurrency.getValue() / toCurrency.getNominal());
        double convertedAmount = Double.parseDouble(String.format("%.2f", amount * rate).replace(",", "."));
        saveConversion(from.toUpperCase(), amount, to.toUpperCase(), rate, convertedAmount);
        return convertedAmount;
    }

    @Override
    public Map<String, String> getSupportedCurrencies() {
        return currencyConfig.getCodes();
    }

    @Override
    public List<String> getSupportedCurrenciesCharCode() {
        Map<String, String> supported = currencyConfig.getCodes();
        return supported.keySet().stream().toList();
    }

    /**
     * Сохраняет информацию о конвертации в базе данных.
     *
     * @param fromCurrency           Код валюты, из которой происходит конвертация.
     * @param amountBeforeConversion Количество валюты до конвертации.
     * @param toCurrency             Код валюты, в которую происходит конвертация.
     * @param conversionRate         Курс конвертации.
     * @param amountAfterConversion  Количество валюты после конвертации.
     */
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

    /**
     * Устанавливает значения по умолчанию для валюты RUB.
     *
     * @return Объект {@link Currency} с установленными значениями.
     */
    public Currency setRUB() {
        Currency currency = new Currency();
        currency.setNominal(1);
        currency.setValue(1);
        return currency;
    }
}

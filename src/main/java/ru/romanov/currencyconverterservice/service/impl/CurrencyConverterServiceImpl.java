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

    /**
     * Получает текущие курсы валют с помощью клиента обменного курса.
     *
     * @return объект CurrencyResponse с текущими курсами валют
     */
    @Override
    public CurrencyResponse getDailyCurrencyRates() {
        return exchangeRateClient.getDailyCurrencyRates();
    }

    /**
     * Выполняет конвертацию указанного количества валюты из одной валюты в другую.
     *
     * @param from   код валюты, из которой выполняется конвертация
     * @param to     код валюты, в которую выполняется конвертация
     * @param amount количество валюты для конвертации
     * @return сконвертированная сумма в указанной валюте или Double.NaN, если конвертация невозможна
     */
    @Override
    public double convert(String from, String to, double amount) {
        if (!getSupportedCurrenciesCharCode().contains(from) || !getSupportedCurrenciesCharCode().contains(to)) {
            return Double.NaN;
        }
        CurrencyResponse response = getDailyCurrencyRates();
        Currency fromCurrency = (from.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(from.toUpperCase()));
        Currency toCurrency = (to.equalsIgnoreCase("RUB") ? setRUB() : response.getValute().get(to.toUpperCase()));

        if (fromCurrency == null || toCurrency == null) {
            return Double.NaN;
        }
        if (fromCurrency.getValue() == 0 || fromCurrency.getNominal() == 0 || toCurrency.getValue() == 0 || toCurrency.getNominal() == 0) {
            return Double.NaN;
        }
        double amountFrom = fromCurrency.getValue() / fromCurrency.getNominal();
        double amountTo = toCurrency.getValue() / toCurrency.getNominal();
        double rate = amountFrom / amountTo;
        double convertedAmount = Double.parseDouble(String.format("%.2f", amount * rate).replace(",", "."));
        saveConversion(from.toUpperCase(), amount, to.toUpperCase(), rate, convertedAmount);
        return convertedAmount;
    }

    /**
     * Получает карту поддерживаемых валют.
     *
     * @return карта, где ключ - код валюты, значение - название валюты
     */
    @Override
    public Map<String, String> getSupportedCurrencies() {
        return currencyConfig.getCodes();
    }

    /**
     * Получает список поддерживаемых кодов валют.
     *
     * @return список строк с кодами валют
     */
    @Override
    public List<String> getSupportedCurrenciesCharCode() {
        Map<String, String> supported = currencyConfig.getCodes();
        return supported.keySet().stream().toList();
    }

    /**
     * Сохраняет информацию о конверсии в базе данных.
     *
     * @param fromCurrency           код валюты, из которой происходит конверсия
     * @param amountBeforeConversion количество валюты до конверсии
     * @param toCurrency             код валюты, в которую происходит конверсия
     * @param conversionRate         курс конверсии
     * @param amountAfterConversion  количество валюты после конверсии
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
     * @return объект Currency с установленными значениями
     */
    public Currency setRUB() {
        Currency currency = new Currency();
        currency.setNominal(1);
        currency.setValue(1);
        return currency;
    }
}

package ru.romanov.currencyconverterservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;
import ru.romanov.currencyconverterservice.model.Currency;
import ru.romanov.currencyconverterservice.model.CurrencyConversion;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.repository.CurrencyConversionRepository;
import ru.romanov.currencyconverterservice.service.CurrencyConverterService;
import ru.romanov.currencyconverterservice.service.cache.CacheCurrencyConverterService;

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

    private final CurrencyConfig currencyConfig;
    private final CurrencyConversionRepository currencyConversionRepository;
    private final CacheCurrencyConverterService cacheCurrencyConverterService;

    @Override
    public CurrencyResponse getDailyCurrencyRates() {
        return cacheCurrencyConverterService.getDailyCurrencyRates();
    }

    @Override
    public double convert(String from, String to, double amount) {
        double rate = getCurrencyRate(from, to);
        double convertedAmount = getFormatedDouble(amount * rate);

        saveConversion(from.toUpperCase(), amount, to.toUpperCase(), rate, convertedAmount);

        return convertedAmount;
    }

    private static double getFormatedDouble(double value) {
        return Double.parseDouble(String.format("%.2f", value).replace(",", "."));
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
    public void saveConversion(
            String fromCurrency,
            double amountBeforeConversion,
            String toCurrency,
            double conversionRate,
            double amountAfterConversion
    ) {
        currencyConversionRepository.save(
                CurrencyConversion.builder()
                        .currencyFrom(fromCurrency)
                        .currencyTo(toCurrency)
                        .amountBeforeConversion(amountBeforeConversion)
                        .conversionRate(conversionRate)
                        .amountAfterConversion(amountAfterConversion)
                        .conversionDate(LocalDateTime.now())
                        .build());
    }

    /**
     * Устанавливает значения по умолчанию для валюты RUB.
     *
     * @return Объект {@link Currency} с установленными значениями.
     */
    public Currency setRUB() {
        return Currency.builder()
                .nominal(1)
                .value(1)
                .build();
    }

    private boolean isInvalidCurrency(String currency) {
        return !getSupportedCurrenciesCharCode().contains(currency);
    }

    public double getCurrencyRate(String from, String to) {
        if (isInvalidCurrency(from) || isInvalidCurrency(to)) return Double.NaN;

        var response = getDailyCurrencyRates().getValute();
        Currency fromCurrency = (from.equalsIgnoreCase("RUB") ? setRUB() : response.get(from.toUpperCase()));
        Currency toCurrency = (to.equalsIgnoreCase("RUB") ? setRUB() : response.get(to.toUpperCase()));

        if (
                fromCurrency == null || toCurrency == null
                || fromCurrency.getValue() == 0 || fromCurrency.getNominal() == 0
                || toCurrency.getValue() == 0 || toCurrency.getNominal() == 0
        ) {
            return Double.NaN;
        }

        double fromRate = fromCurrency.getValue() / fromCurrency.getNominal();
        double toRate = toCurrency.getValue() / toCurrency.getNominal();

        return fromRate / toRate;
    }
}

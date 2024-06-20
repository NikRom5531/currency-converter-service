package ru.romanov.currencyconverterservice.service;

import ru.romanov.currencyconverterservice.model.CurrencyResponse;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса конвертации валют.
 * <p>
 * Этот интерфейс определяет методы для получения текущих курсов валют, конвертации валют,
 * получения поддерживаемых валют и получения списка поддерживаемых кодов валют.
 */
public interface CurrencyConverterService {
    CurrencyResponse getDailyCurrencyRates();

    double convert(String from, String to, double amount);

    Map<String, String> getSupportedCurrencies();

    List<String> getSupportedCurrenciesCharCode();
}

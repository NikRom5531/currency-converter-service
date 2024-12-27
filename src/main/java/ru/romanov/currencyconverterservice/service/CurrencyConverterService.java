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
    /**
     * Получает текущие курсы валют с помощью клиента обменного курса.
     *
     * @return Объект {@link CurrencyResponse} с текущими курсами валют.
     */
    CurrencyResponse getDailyCurrencyRates();

    /**
     * Выполняет конвертацию указанного количества валюты из одной валюты в другую.
     *
     * @param from   Код валюты, из которой выполняется конвертация.
     * @param to     Код валюты, в которую выполняется конвертация.
     * @param amount Количество валюты для конвертации.
     * @return Сконвертированная сумма в указанной валюте или {@code Double.NaN}, если конвертация невозможна.
     */
    double convert(String from, String to, double amount);

    /**
     * Получает карту поддерживаемых валют.
     *
     * @return Карта, где ключ - код валюты, значение - название валюты.
     */
    Map<String, String> getSupportedCurrencies();

    /**
     * Получает список поддерживаемых кодов валют.
     *
     * @return Список строк с кодами валют.
     */
    List<String> getSupportedCurrenciesCharCode();

    double getCurrencyRate(String fromCurrency, String toCurrency);
}

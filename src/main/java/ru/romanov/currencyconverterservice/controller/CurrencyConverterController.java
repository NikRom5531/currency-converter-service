package ru.romanov.currencyconverterservice.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки запросов, связанных с конвертацией валют.
 * <p>
 * Этот контроллер предоставляет эндпоинты для получения текущих курсов валют,
 * конвертации суммы из одной валюты в другую, а также получения поддерживаемых валют.
 */
public interface CurrencyConverterController {

    /**
     * Получение текущих курсов валют.
     *
     * @return {@link ResponseEntity} с объектом {@link CurrencyResponse}, содержащим текущие курсы валют.
     */
    @GetMapping("/rates")
    ResponseEntity<CurrencyResponse> getCurrencyRates();

    /**
     * Конвертация суммы из одной валюты в другую.
     *
     * @param from   код исходной валюты.
     * @param to     код целевой валюты.
     * @param amount сумма для конвертации.
     * @return {@link ResponseEntity} с конвертированной суммой типа {@link Double}.
     */
    @GetMapping("/convert")
    ResponseEntity<Double> convertCurrency(
            @RequestParam @NotBlank @Size(min = 3, max = 3) String from,
            @RequestParam @NotBlank @Size(min = 3, max = 3) String to,
            @RequestParam @NotNull @DecimalMin("0.01") double amount
    );

    /**
     * Получение поддерживаемой карты валют.
     *
     * @return {@link ResponseEntity} с картой поддерживаемых валют (ключ - код валюты, значение - название валюты).
     */
    @GetMapping("/supported-currency-map")
    ResponseEntity<Map<String, String>> getSupportedCurrencies();

    /**
     * Получение списка поддерживаемых кодов валют.
     *
     * @return {@link ResponseEntity} со списком строк, представляющих коды поддерживаемых валют.
     */
    @GetMapping("/supported-codes")
    ResponseEntity<List<String>> getSupportedCurrenciesCharCodes();

    @GetMapping("/api/currency/rates")
    ResponseEntity<Double> getCurrencyRate(String fromCurrencyCharCode, String toCurrencyCharCode);
}

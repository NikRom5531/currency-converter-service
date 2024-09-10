package ru.romanov.currencyconverterservice.controller;

import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.service.CurrencyConverterService;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки запросов, связанных с конвертацией валют.
 * <p>
 * Этот контроллер предоставляет эндпоинты для получения текущих курсов валют,
 * конвертации суммы из одной валюты в другую, а также получения поддерживаемых валют.
 */
@RestController
@RequestMapping("/api/currency")
@AllArgsConstructor
public class CurrencyConverterController {
    private CurrencyConverterService currencyConverterService;
    private final Bucket bucket;

    /**
     * Получение текущих курсов валют.
     *
     * @return {@link ResponseEntity} с объектом {@link CurrencyResponse}, содержащим текущие курсы валют.
     */
    @GetMapping("/rates")
    public ResponseEntity<CurrencyResponse> getCurrencyRates() {
        if (bucket.tryConsume(1)) {
            CurrencyResponse convertedAmount = currencyConverterService.getDailyCurrencyRates();
            return ResponseEntity.ok(convertedAmount);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Конвертация суммы из одной валюты в другую.
     *
     * @param from   код исходной валюты.
     * @param to     код целевой валюты.
     * @param amount сумма для конвертации.
     * @return {@link ResponseEntity} с конвертированной суммой типа {@link Double}.
     */
    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(currencyConverterService.convert(from, to, amount));
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Получение поддерживаемой карты валют.
     *
     * @return {@link ResponseEntity} с картой поддерживаемых валют (ключ - код валюты, значение - название валюты).
     */
    @GetMapping("/supported-currency-map")
    public ResponseEntity<Map<String, String>> getSupportedCurrencies() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(currencyConverterService.getSupportedCurrencies());
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Получение списка поддерживаемых кодов валют.
     *
     * @return {@link ResponseEntity} со списком строк, представляющих коды поддерживаемых валют.
     */
    @GetMapping("/supported-codes")
    public ResponseEntity<List<String>> getSupportedCurrenciesCharCodes() {
        if (bucket.tryConsume(1)) {
            List<String> supportedCurrencies = currencyConverterService.getSupportedCurrenciesCharCode();
            return ResponseEntity.ok(supportedCurrencies);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }
}

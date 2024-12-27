package ru.romanov.currencyconverterservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.currencyconverterservice.controller.CurrencyConverterController;
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
@RequiredArgsConstructor
public class DefaultCurrencyConverterController implements CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;

    @Override
    public ResponseEntity<CurrencyResponse> getCurrencyRates() {
        return ResponseEntity.ok(currencyConverterService.getDailyCurrencyRates());
    }

    @Override
    public ResponseEntity<Double> convertCurrency(String from, String to, double amount) {
        return ResponseEntity.ok(currencyConverterService.convert(from, to, amount));
    }

    @Override
    public ResponseEntity<Map<String, String>> getSupportedCurrencies() {
        return ResponseEntity.ok(currencyConverterService.getSupportedCurrencies());
    }

    @Override
    public ResponseEntity<List<String>> getSupportedCurrenciesCharCodes() {
        return ResponseEntity.ok(currencyConverterService.getSupportedCurrenciesCharCode());
    }

    @Override
    public ResponseEntity<Double> getCurrencyRate(String fromCurrency, String toCurrency) {
        return ResponseEntity.ok(currencyConverterService.getCurrencyRate(fromCurrency, toCurrency));
    }
}

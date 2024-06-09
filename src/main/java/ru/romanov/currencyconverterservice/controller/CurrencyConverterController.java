package ru.romanov.currencyconverterservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.service.CurrencyConverterService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @GetMapping("/rates")
    public ResponseEntity<CurrencyResponse> getCurrencyRates() {
        CurrencyResponse convertedAmount = currencyConverterService.getDailyCurrencyRates();
        return ResponseEntity.ok(convertedAmount);
    }

    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {
        return ResponseEntity.ok(currencyConverterService.convert(from, to, amount));
    }

    @GetMapping("/supported")
    public ResponseEntity<List<String>> getSupportedCurrencies() {
        List<String> supportedCurrencies = currencyConverterService.getSupportedCurrencies();
        return ResponseEntity.ok(supportedCurrencies);
    }
}

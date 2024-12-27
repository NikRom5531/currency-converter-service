package ru.romanov.currencyconverterservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.service.CurrencyConverterService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterControllerTest {

    @Mock
    private CurrencyConverterService currencyConverterService;

    @InjectMocks
    private CurrencyConverterController currencyConverterController;

    @Test
    void testGetCurrencyRates_Success() {
        CurrencyResponse response = new CurrencyResponse();
        when(currencyConverterService.getDailyCurrencyRates()).thenReturn(response);

        ResponseEntity<CurrencyResponse> result = currencyConverterController.getCurrencyRates();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetCurrencyRates_TooManyRequests() {
        ResponseEntity<CurrencyResponse> result = currencyConverterController.getCurrencyRates();

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testConvertCurrency_Success() {
        when(currencyConverterService.convert("USD", "EUR", 100.0)).thenReturn(88.24);

        ResponseEntity<Double> result = currencyConverterController.convertCurrency("USD", "EUR", 100.0);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(88.24, result.getBody());
    }

    @Test
    void testConvertCurrency_TooManyRequests() {
        ResponseEntity<Double> result = currencyConverterController.convertCurrency("USD", "EUR", 100.0);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetSupportedCurrencies_Success() {
        Map<String, String> supportedCurrencies = Map.of("USD", "Доллар США", "EUR", "Евро");
        when(currencyConverterService.getSupportedCurrencies()).thenReturn(supportedCurrencies);

        ResponseEntity<Map<String, String>> result = currencyConverterController.getSupportedCurrencies();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(supportedCurrencies, result.getBody());
    }

    @Test
    void testGetSupportedCurrencies_TooManyRequests() {
        ResponseEntity<Map<String, String>> result = currencyConverterController.getSupportedCurrencies();

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetSupportedCurrenciesCharCodes_Success() {
        List<String> supportedCodes = List.of("USD", "EUR");
        when(currencyConverterService.getSupportedCurrenciesCharCode()).thenReturn(supportedCodes);

        ResponseEntity<List<String>> result = currencyConverterController.getSupportedCurrenciesCharCodes();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(supportedCodes, result.getBody());
    }

    @Test
    void testGetSupportedCurrenciesCharCodes_TooManyRequests() {
        ResponseEntity<List<String>> result = currencyConverterController.getSupportedCurrenciesCharCodes();

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.getStatusCode());
        assertNull(result.getBody());
    }
}

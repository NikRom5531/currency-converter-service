package ru.romanov.currencyconverterservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.romanov.currencyconverterservice.client.ExchangeRateClient;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;
import ru.romanov.currencyconverterservice.model.Currency;
import ru.romanov.currencyconverterservice.model.CurrencyConversion;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;
import ru.romanov.currencyconverterservice.repository.CurrencyConversionRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterServiceImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private CurrencyConfig currencyConfig;

    @Mock
    private CurrencyConversionRepository currencyConversionRepository;

    @InjectMocks
    private CurrencyConverterServiceImpl currencyConverterService;

    @Test
    void testConvert_ValidCurrencies() {
        CurrencyResponse response = new CurrencyResponse();
        Map<String, Currency> valute = new HashMap<>();
        Currency usd = new Currency();
        usd.setValue(75.0);
        usd.setNominal(1);
        Currency eur = new Currency();
        eur.setValue(85.0);
        eur.setNominal(1);
        valute.put("USD", usd);
        valute.put("EUR", eur);
        response.setValute(valute);

        when(exchangeRateClient.getDailyCurrencyRates()).thenReturn(response);
        when(currencyConfig.getCodes()).thenReturn(Map.of("USD", "Доллар США", "EUR", "Евро"));

        double result = currencyConverterService.convert("USD", "EUR", 100);

        assertEquals(88.24, result, 0.01);
        verify(currencyConversionRepository, times(1)).save(any(CurrencyConversion.class));
    }

    @Test
    void testConvert_InvalidCurrency() {
        when(currencyConfig.getCodes()).thenReturn(Map.of("USD", "Доллар США"));

        double result = currencyConverterService.convert("USD", "GBP", 100);

        assertTrue(Double.isNaN(result));
        verify(currencyConversionRepository, never()).save(any());
    }

    @Test
    void testGetDailyCurrencyRates() {
        CurrencyResponse response = new CurrencyResponse();
        when(exchangeRateClient.getDailyCurrencyRates()).thenReturn(response);

        CurrencyResponse result = currencyConverterService.getDailyCurrencyRates();

        assertEquals(response, result);
    }

    @Test
    void testGetSupportedCurrencies() {
        Map<String, String> supportedCurrencies = Map.of("USD", "Доллар США", "EUR", "Евро");
        when(currencyConfig.getCodes()).thenReturn(supportedCurrencies);

        Map<String, String> result = currencyConverterService.getSupportedCurrencies();

        assertEquals(supportedCurrencies, result);
    }

    @Test
    void testSaveConversion() {
        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setCurrencyFrom("USD");
        conversion.setCurrencyTo("EUR");
        conversion.setAmountBeforeConversion(100.0);
        conversion.setConversionRate(0.88);
        conversion.setAmountAfterConversion(88.0);

        currencyConverterService.saveConversion("USD", 100.0, "EUR", 0.88, 88.0);

        verify(currencyConversionRepository, times(1)).save(any(CurrencyConversion.class));
    }
}

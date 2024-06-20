package ru.romanov.currencyconverterservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Класс представляет ответ на запрос о курсах валют.
 * <p>
 * Каждый объект данного класса содержит информацию о текущих курсах валют на определённую дату.
 */
@Getter
@Setter
public class CurrencyResponse {
    @JsonProperty("Date")
    private String date;

    @JsonProperty("PreviousDate")
    private String previousDate;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Valute")
    private Map<String, Currency> valute;
}

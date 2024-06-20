package ru.romanov.currencyconverterservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель представления валюты, используемая для десериализации JSON данных о валюте.
 * <p>
 * Каждое поле соответствует соответствующему свойству в JSON объекте о валюте.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @JsonProperty("ID")
    private String id;

    @JsonProperty("NumCode")
    private String numCode;

    @JsonProperty("CharCode")
    private String charCode;

    @JsonProperty("Nominal")
    private int nominal;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private double value;

    @JsonProperty("Previous")
    private double previous;
}

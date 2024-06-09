package ru.romanov.currencyconverterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "currency_conversion")
@Getter
@Setter
public class CurrencyConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversion_date", nullable = false, updatable = false)
    private LocalDateTime conversionDate;

    @Column(name = "currency_code_from", nullable = false)
    private String currencyFrom;

    @Column(name = "amount_before_conversion", nullable = false)
    private double amountBeforeConversion;

    @Column(name = "currency_code_to", nullable = false)
    private String currencyTo;

    @Column(name = "conversion_rate", nullable = false)
    private double conversionRate;

    @Column(name = "amount_after_conversion", nullable = false)
    private double amountAfterConversion;
}
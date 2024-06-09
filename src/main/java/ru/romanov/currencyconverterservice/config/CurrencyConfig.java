package ru.romanov.currencyconverterservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "currencies")
public class CurrencyConfig {
    private Map<String, String> codes;

    @Override
    public String toString() {
        return "CurrencyConfig{" +
                "codes=" + codes +
                '}';
    }
}

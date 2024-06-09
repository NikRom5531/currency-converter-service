package ru.romanov.currencyconverterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.romanov.currencyconverterservice.config.CurrencyConfig;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(CurrencyConfig.class)
public class CurrencyConverterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterServiceApplication.class, args);
    }

}

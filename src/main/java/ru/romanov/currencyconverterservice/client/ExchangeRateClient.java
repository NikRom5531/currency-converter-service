package ru.romanov.currencyconverterservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.romanov.currencyconverterservice.model.CurrencyResponse;

/**
 * Клиент Feign для взаимодействия с внешним сервисом обменных курсов.
 * <p>
 * Этот интерфейс используется для получения данных о текущих обменных курсах
 * от внешнего сервиса. Интерфейс аннотирован {@code @FeignClient}, что позволяет
 * Spring использовать его как клиент для взаимодействия с другим микросервисом.
 * <p>
 * Аннотация {@code @FeignClient} определяет имя клиента (в данном случае "exchange-rate-client")
 * и URL-адрес внешнего сервиса, указанный в свойстве {@code exchange.rate.service.url}.
 * <p>
 * Метод {@code getDailyCurrencyRates()} выполняет HTTP GET запрос на эндпоинт {@code /daily_json.js}
 * и ожидает получить ответ в формате {@link CurrencyResponse}.
 */
@FeignClient(name = "exchange-rate-client", url = "${exchange.rate.service.url}")
public interface ExchangeRateClient {
    /**
     * Получение текущих обменных курсов.
     *
     * @return Объект {@link CurrencyResponse}, содержащий данные о текущих обменных курсах.
     */
    @GetMapping("/daily_json.js")
    CurrencyResponse getDailyCurrencyRates();
}

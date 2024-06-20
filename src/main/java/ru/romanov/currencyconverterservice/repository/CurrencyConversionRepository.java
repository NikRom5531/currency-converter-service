package ru.romanov.currencyconverterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.currencyconverterservice.model.CurrencyConversion;

/**
 * Репозиторий для взаимодействия с сущностью конверсии валют в базе данных.
 * <p>
 * Данный интерфейс предоставляет методы для сохранения, обновления, удаления и поиска
 * объектов CurrencyConversion в базе данных. Он расширяет JpaRepository, что позволяет
 * использовать стандартные методы доступа к данным.
 */
@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, Long> {
}
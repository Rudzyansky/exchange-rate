package ru.ft.exchangerate.services;

import org.springframework.stereotype.Service;
import ru.ft.exchangerate.openexchangerates.ExchangeRates;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesClient;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class OpenExchangeRatesService {

    private final Clock clock;
    private final OpenExchangeRatesClient openExchangeRatesClient;

    private final DateTimeFormatter formatter;

    public OpenExchangeRatesService(Clock clock, OpenExchangeRatesClient openExchangeRatesClient) {
        this.clock = clock;
        this.openExchangeRatesClient = openExchangeRatesClient;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    private double getRateByDate(String currency, LocalDate date) {
        if (currency.length() != 3) throw OpenExchangeRatesException.NO_CURRENCY_FOUND;

        ExchangeRates exchangeRates = openExchangeRatesClient.getHistorical(date.format(formatter), currency);

        if (exchangeRates.rates.isEmpty()) throw OpenExchangeRatesException.NO_CURRENCY_FOUND;
        return exchangeRates.rates.get(currency);
    }

    public double yesterday(String currency) {
        return getRateByDate(currency, LocalDate.now(clock).minusDays(1));
    }

    public double today(String currency) {
        return getRateByDate(currency, LocalDate.now(clock));
    }
}

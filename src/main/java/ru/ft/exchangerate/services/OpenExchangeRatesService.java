package ru.ft.exchangerate.services;

import org.springframework.stereotype.Service;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesClient;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class OpenExchangeRatesService {

    private final Clock clock;
    private final OpenExchangeRatesClient openExchangeRatesClient;

    public OpenExchangeRatesService(Clock clock, OpenExchangeRatesClient openExchangeRatesClient) {
        this.clock = clock;
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    public double yesterday(String currency) {
        LocalDate date = LocalDate.now(clock).minusDays(1);
        String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return openExchangeRatesClient.getHistorical(formatted, currency).rates.get(currency);
    }

    public double today(String currency) {
        LocalDate date = LocalDate.now(clock);
        String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return openExchangeRatesClient.getHistorical(formatted, currency).rates.get(currency);
    }
}

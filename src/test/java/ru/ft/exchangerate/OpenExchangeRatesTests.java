package ru.ft.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesClient;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesException;
import ru.ft.exchangerate.services.OpenExchangeRatesService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OpenExchangeRatesTests {

    private static final String currency = "RUB";
    private static final String today = "2022-06-06";
    private static final double todayRate = 61.374999;
    private static final double yesterdayRate = 63.250003;

    @TestConfiguration
    public static class TestConfig {
        @Bean(name = "fixedClock")
        @Primary
        public Clock getClock() {
            return Clock.fixed(Instant.parse(today + "T12:00:00.00Z"), ZoneId.of("UTC"));
        }
    }

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;

    @Test
    public void historicalNotAvailableTest() {
        Exception exception = assertThrows(OpenExchangeRatesException.class, () ->
                openExchangeRatesClient.getHistorical("0001-01-01", currency)
        );
        String expected = "Historical rates for the requested date are not available";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected), actual);
    }

    @Test
    public void historicalWrongDateTest() {
        Exception exception = assertThrows(OpenExchangeRatesException.class, () ->
                openExchangeRatesClient.getHistorical("0000-00-00", currency)
        );
        // В документации сказано про это сообщение, но его поймать невозможно: The date you requested is invalid
        String expected = "Historical API queries require a valid date in the format `YYYY-MM-DD.json`";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected), actual);
    }

    @Test
    public void historicalWrongCurrencyTest() {
        Exception exception = assertThrows(OpenExchangeRatesException.class, () ->
                // Тут у меня претензии к сервису. Должна быть ошибка, но вместо этого пустой список
                openExchangeRatesService.today("wrong_currency")
        );
        String expected = OpenExchangeRatesException.NO_CURRENCY_FOUND.getMessage();
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected), actual);
    }

    @Test
    public void todayTest() {
        double actual = openExchangeRatesService.today(currency);
        assertEquals(todayRate, actual);
    }

    @Test
    public void yesterdayTest() {
        double actual = openExchangeRatesService.yesterday(currency);
        assertEquals(yesterdayRate, actual);
    }
}

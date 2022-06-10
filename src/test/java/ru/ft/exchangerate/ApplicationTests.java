package ru.ft.exchangerate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import ru.ft.exchangerate.giphy.GifsGiphyClient;
import ru.ft.exchangerate.giphy.MediaGiphyClient;
import ru.ft.exchangerate.giphy.SingleResponse;
import ru.ft.exchangerate.openexchangerates.ExchangeRates;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesClient;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MediaGiphyClient mediaGiphyClient;

    @MockBean
    private GifsGiphyClient gifsGiphyClient;

    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClient;

    private static final String currency = "RUB";
    private static final String today = "2022-06-06";
    private static final String yesterday = "2022-06-05";

    private static ExchangeRates exchangeRates60;
    private static ExchangeRates exchangeRates70;

    private static SingleResponse responseRich;
    private static SingleResponse responseBroke;

    private static byte[] responseMediaRich;
    private static byte[] responseMediaBroke;

    @TestConfiguration
    public static class TestConfig {
        @Bean(name = "fixedClock")
        @Primary
        public Clock getClock() {
            return Clock.fixed(Instant.parse(today + "T12:00:00.00Z"), ZoneId.of("UTC"));
        }
    }

    @BeforeAll
    static void setup() {
        responseMediaRich = "rich".getBytes();
        responseMediaBroke = "broke".getBytes();

        responseRich = new SingleResponse() {{
            meta = new MetaObject() {{
                status = 200;
                msg = "OK";
            }};
            data = new GifObject() {{
                id = "rich";
            }};
        }};

        responseBroke = new SingleResponse() {{
            meta = new MetaObject() {{
                status = 200;
                msg = "OK";
            }};
            data = new GifObject() {{
                id = "broke";
            }};
        }};

        exchangeRates60 = new ExchangeRates() {{
            rates = new HashMap<>() {{
                put(currency, 60.0);
            }};
        }};

        exchangeRates70 = new ExchangeRates() {{
            rates = new HashMap<>() {{
                put(currency, 70.0);
            }};
        }};
    }

    @Test
    void richTest() throws Exception {
        when(mediaGiphyClient.gif("broke")).thenReturn(responseMediaBroke);
        when(mediaGiphyClient.gif("rich")).thenReturn(responseMediaRich);

        when(gifsGiphyClient.getRandom("rich")).thenReturn(responseRich);
        when(gifsGiphyClient.getRandom("broke")).thenReturn(responseBroke);

        when(openExchangeRatesClient.getHistorical(yesterday, currency)).thenReturn(exchangeRates70);
        when(openExchangeRatesClient.getHistorical(today, currency)).thenReturn(exchangeRates60);

        byte[] actual = mvc.perform(get("/v1/gifs/currency-fluctuations/" + currency))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        assertArrayEquals(responseMediaRich, actual);
    }

    @Test
    void brokeTest() throws Exception {
        when(mediaGiphyClient.gif("broke")).thenReturn(responseMediaBroke);
        when(mediaGiphyClient.gif("rich")).thenReturn(responseMediaRich);

        when(gifsGiphyClient.getRandom("rich")).thenReturn(responseRich);
        when(gifsGiphyClient.getRandom("broke")).thenReturn(responseBroke);

        when(openExchangeRatesClient.getHistorical(yesterday, currency)).thenReturn(exchangeRates60);
        when(openExchangeRatesClient.getHistorical(today, currency)).thenReturn(exchangeRates70);

        byte[] actual = mvc.perform(get("/v1/gifs/currency-fluctuations/" + currency))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        assertArrayEquals(responseMediaBroke, actual);
    }

    @Test
    void equalsTest() throws Exception {
        when(mediaGiphyClient.gif("broke")).thenReturn(responseMediaBroke);
        when(mediaGiphyClient.gif("rich")).thenReturn(responseMediaRich);

        when(gifsGiphyClient.getRandom("rich")).thenReturn(responseRich);
        when(gifsGiphyClient.getRandom("broke")).thenReturn(responseBroke);

        when(openExchangeRatesClient.getHistorical(yesterday, currency)).thenReturn(exchangeRates60);
        when(openExchangeRatesClient.getHistorical(today, currency)).thenReturn(exchangeRates60);

        byte[] actual = mvc.perform(get("/v1/gifs/currency-fluctuations/" + currency))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        assertArrayEquals(responseMediaBroke, actual);
    }

}

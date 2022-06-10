package ru.ft.exchangerate.openexchangerates;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "open-exchange-rates", url = "${open-exchange-rates.url}", path = "/api", configuration = OpenExchangeRatesConfiguration.class)
public interface OpenExchangeRatesClient {

    @GetMapping("/historical/{date}.json?app_id=${open-exchange-rates.app-id}&base=${open-exchange-rates.base-currency}&symbols={targetCurrency}")
    ExchangeRates getHistorical(@PathVariable String date, @PathVariable String targetCurrency);
}

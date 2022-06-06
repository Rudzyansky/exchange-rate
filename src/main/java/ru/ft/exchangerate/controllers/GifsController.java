package ru.ft.exchangerate.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ft.exchangerate.services.GiphyService;
import ru.ft.exchangerate.services.OpenExchangeRatesService;
import ru.ft.exchangerate.services.ProcessingService;

@RestController
@RequestMapping("/v1/gifs")
public class GifsController {

    private final OpenExchangeRatesService openExchangeRatesService;
    private final GiphyService giphyService;
    private final ProcessingService processingService;

    public GifsController(OpenExchangeRatesService openExchangeRatesService, GiphyService giphyService, ProcessingService processingService) {
        this.openExchangeRatesService = openExchangeRatesService;
        this.giphyService = giphyService;
        this.processingService = processingService;
    }

    @GetMapping(value = "/currency-fluctuations/{currency}", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getCurrencyFluctuations(@PathVariable String currency) {
        double yesterday = openExchangeRatesService.yesterday(currency);
        double today = openExchangeRatesService.today(currency);
        String tag = processingService.tagByExchangeRates(today, yesterday);
        return giphyService.getRandom(tag);
    }
}

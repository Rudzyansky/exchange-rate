package ru.ft.exchangerate.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

    @Value("${giphy.down-tag}")
    private String downTag;

    @Value("${giphy.up-tag}")
    private String upTag;

    @Value("${giphy.no-changes-tag}")
    private String noChangesTag;

    public String tagByExchangeRates(Double today, Double yesterday) {
        return today < yesterday ? downTag : today > yesterday ? upTag : noChangesTag;
    }
}

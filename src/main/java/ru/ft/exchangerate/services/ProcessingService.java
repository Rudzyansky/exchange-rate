package ru.ft.exchangerate.services;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

    private final String downTag;
    private final String upTag;
    private final String noChangesTag;

    public ProcessingService(Environment env) {
        downTag = env.getProperty("giphy.down-tag", "rich");
        upTag = env.getProperty("giphy.up-tag", "broke");
        noChangesTag = env.getProperty("giphy.no-changes-tag", upTag); // не заработали = потеряли (инфляция)
    }

    public String tagByExchangeRates(double today, double yesterday) {
        return today < yesterday ? downTag : today > yesterday ? upTag : noChangesTag;
    }
}

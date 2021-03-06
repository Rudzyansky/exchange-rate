package ru.ft.exchangerate.openexchangerates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class OpenExchangeRatesConfiguration {

    @Bean
    public ErrorDecoder getErrorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
            FeignException feignException = FeignException.errorStatus(methodKey, response);
            try {
                ErrorObject err = objectMapper.readValue(feignException.contentUTF8(), ErrorObject.class);
                return new OpenExchangeRatesException(err.description);
            } catch (JsonProcessingException ignore) {
                return feignException;
            }
        };
    }
}

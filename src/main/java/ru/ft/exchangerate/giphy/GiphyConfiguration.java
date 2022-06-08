package ru.ft.exchangerate.giphy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import ru.ft.exchangerate.openexchangerates.OpenExchangeRatesException;

public class GiphyConfiguration {

    @Bean
    public ErrorDecoder getErrorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
            FeignException feignException = FeignException.errorStatus(methodKey, response);
            try {
                SingleResponse singleResponse = objectMapper.readValue(feignException.contentUTF8(), SingleResponse.class);
                if (singleResponse.meta != null)
                    return new GiphyException(singleResponse.meta.msg);

                // В документации про это ни слова
                ErrorObject errorObject = objectMapper.readValue(feignException.contentUTF8(), ErrorObject.class);
                return new GiphyException(errorObject.message);
            } catch (JsonProcessingException ignore) {
                return feignException;
            }
        };
    }
}

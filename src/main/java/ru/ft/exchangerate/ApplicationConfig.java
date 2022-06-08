package ru.ft.exchangerate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Clock;
import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class ApplicationConfig {

    @Bean
    public Clock getClock(Environment env) {
        String zoneId = env.getProperty("app.timezone", TimeZone.getDefault().getID());
        return Clock.system(ZoneId.of(zoneId));
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
    }
}

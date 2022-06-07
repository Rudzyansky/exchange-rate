package ru.ft.exchangerate;

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
}

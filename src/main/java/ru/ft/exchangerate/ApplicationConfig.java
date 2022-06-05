package ru.ft.exchangerate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationConfig {
    @Bean
    Clock getClock() {
        return Clock.systemDefaultZone();
    }
}

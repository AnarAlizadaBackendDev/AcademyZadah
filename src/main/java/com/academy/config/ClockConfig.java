package com.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

    /** Month unlocking follows Baku time. */
    @Bean
    Clock clock() {
        return Clock.system(ZoneId.of("Asia/Baku"));
    }
}

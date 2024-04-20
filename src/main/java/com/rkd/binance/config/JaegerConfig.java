package com.rkd.binance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        return new io.jaegertracing.Configuration(applicationName).getTracer();
    }
}

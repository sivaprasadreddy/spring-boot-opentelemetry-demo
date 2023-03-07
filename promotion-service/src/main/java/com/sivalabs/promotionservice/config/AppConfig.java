package com.sivalabs.promotionservice.config;

import com.sivalabs.promotionservice.ApplicationProperties;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Bean
    JaegerGrpcSpanExporter otelJaegerGrpcSpanExporter(ApplicationProperties properties) {
        JaegerGrpcSpanExporterBuilder builder = JaegerGrpcSpanExporter.builder();
        String endpoint = properties.getJaeger().getEndpoint();
        if (StringUtils.hasText(endpoint)) {
            builder.setEndpoint(endpoint);
        }
        Long timeout = properties.getJaeger().getTimeout();
        if (timeout != null) {
            builder.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        return builder.build();
    }
}

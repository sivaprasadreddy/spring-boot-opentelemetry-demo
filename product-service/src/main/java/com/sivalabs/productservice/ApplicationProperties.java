package com.sivalabs.productservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ApplicationProperties {
    private String promotionServiceUrl;
    private Jaeger jaeger = new Jaeger();

    @Setter
    @Getter
    public static class Jaeger {
        private boolean enabled = true;
        private Long timeout;
        private String endpoint;
    }
}
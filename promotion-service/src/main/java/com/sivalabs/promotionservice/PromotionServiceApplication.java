package com.sivalabs.promotionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PromotionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionServiceApplication.class, args);
    }

}

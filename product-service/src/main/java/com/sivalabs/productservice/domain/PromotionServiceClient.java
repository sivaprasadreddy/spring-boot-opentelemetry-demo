package com.sivalabs.productservice.domain;

import com.sivalabs.productservice.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionServiceClient {

    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    public List<Promotion> getProductPromotions() {
        try {
            log.info("Fetching promotions from {}", properties.getPromotionServiceUrl()+"/api/promotions");
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<List<Promotion>> response = restTemplate.exchange(
                    properties.getPromotionServiceUrl()+"/api/promotions", HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<>() {});
            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.sivalabs.productservice.controller;

import com.sivalabs.productservice.domain.Product;
import com.sivalabs.productservice.domain.ProductRepository;
import com.sivalabs.productservice.domain.Promotion;
import com.sivalabs.productservice.domain.PromotionServiceClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    PromotionServiceClient promotionServiceClient;

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.2-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        Product p1 = new Product(1L, "Lenovo Laptop", new BigDecimal(65000));
        repository.save(p1);
    }

    @Test
    void shouldReturnAllProductsWithPromotions() throws Exception {
        given(promotionServiceClient.getProductPromotions()).willReturn(
                List.of(new Promotion(null, 1L, new BigDecimal(3000)))
        );
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Lenovo Laptop")))
                .andExpect(jsonPath("$[0].originalPrice", Matchers.equalTo(65000.0)))
                .andExpect(jsonPath("$[0].discount", Matchers.equalTo(3000)))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(62000.0)));
    }

    @Test
    void shouldReturnAllProductsWithoutPromotions() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Lenovo Laptop")))
                .andExpect(jsonPath("$[0].originalPrice", Matchers.equalTo(65000.0)))
                .andExpect(jsonPath("$[0].discount", Matchers.equalTo(0)))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(65000.0)));
    }
}
package com.sivalabs.productservice.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private Long id;
    private Long productId;
    private BigDecimal discount;
}

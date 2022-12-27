package com.code4info.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    Long id;
    String name;
    BigDecimal currentPrice;
    Date lastUpdate;
}

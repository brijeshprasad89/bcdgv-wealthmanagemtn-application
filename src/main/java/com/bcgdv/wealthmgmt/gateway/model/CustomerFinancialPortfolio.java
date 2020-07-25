package com.bcgdv.wealthmgmt.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFinancialPortfolio {

    private Integer customerId;
    private BigDecimal stocks;
    private BigDecimal bonds;
    private BigDecimal cash;
}

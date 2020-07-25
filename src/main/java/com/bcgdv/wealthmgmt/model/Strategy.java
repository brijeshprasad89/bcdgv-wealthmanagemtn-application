package com.bcgdv.wealthmgmt.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Builder
@Slf4j
public class Strategy {

    private Integer strategyId;
    private Integer minRiskLevel;
    private Integer maxRiskLevel;
    private Integer minYearsToRetirement;
    private Integer maxYearsToRetirement;
    private Integer stocksPercentage;
    private Integer cashPercentage;
    private Integer bondsPercentage;


    public boolean isAssignableToCustomer(Customer customer) {
        if ((customer.getRiskLevel() >= minRiskLevel && customer.getRiskLevel() <= maxRiskLevel) &&
                customer.getYearsUntilRetirement() >= minYearsToRetirement && customer.getYearsUntilRetirement() <= maxYearsToRetirement) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}

package com.bcgdv.wealthmgmt.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Customer {
    private Integer customerId;
    private String email;
    private LocalDate dateOfBirth;
    private Integer riskLevel;
    private Integer retirementAge;

    public Integer getYearsUntilRetirement() {
        return retirementAge - (LocalDate.now().getYear() - dateOfBirth.getYear());
    }
}

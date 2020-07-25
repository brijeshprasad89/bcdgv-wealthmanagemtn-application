package com.bcgdv.wealthmgmt.data.mapper;

import com.bcgdv.wealthmgmt.common.Utils;
import com.bcgdv.wealthmgmt.model.Customer;
import org.apache.commons.csv.CSVRecord;



public class CustomerMapper {

    public static final String[] FILE_HEADER_MAPPING = {"customerId", "email", "dateOfBirth", "riskLevel", "retirementAge"};

    private static final String CUSTOMER_ID = "customerId";
    private static final String EMAIL = "email";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String RISK_LEVEL = "riskLevel";
    private static final String RETIREMENT_AGE = "retirementAge";

    public static Customer mapRecord(CSVRecord record) {
        return Customer.builder()
                .customerId(Utils.csvStringRecordToInteger(record, CUSTOMER_ID))
                .dateOfBirth(Utils.csvStringRecordToDate(record, DATE_OF_BIRTH))
                .email(record.get(EMAIL))
                .retirementAge(Utils.csvStringRecordToInteger(record, RETIREMENT_AGE))
                .riskLevel(Utils.csvStringRecordToInteger(record, RISK_LEVEL))
                .build();
    }
}

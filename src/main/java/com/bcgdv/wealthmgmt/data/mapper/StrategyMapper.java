package com.bcgdv.wealthmgmt.data.mapper;

import com.bcgdv.wealthmgmt.common.Utils;
import com.bcgdv.wealthmgmt.model.Customer;
import com.bcgdv.wealthmgmt.model.Strategy;
import org.apache.commons.csv.CSVRecord;

public class StrategyMapper {

    public static final String[] FILE_HEADER_MAPPING = {"strategyId", "minRiskLevel", "maxRiskLevel", "minYearsToRetirement", "maxYearsToRetirement", "stocksPercentage", "cashPercentage", "bondsPercentage"};

    private static final String STRATEGY_ID = "strategyId";
    private static final String MIN_RISK_LEVEL = "minRiskLevel";
    private static final String MAX_RISK_LEVEL = "maxRiskLevel";
    private static final String MIN_YEARS_TO_RETIREMENT = "minYearsToRetirement";
    private static final String MAX_YEARS_TO_RETIREMENT = "maxYearsToRetirement";
    private static final String STOCKS_PERCENTAGE = "stocksPercentage";
    private static final String CASH_PERCENTAGE = "cashPercentage";
    private static final String BONDS_PERCENTAGE = "bondsPercentage";


    public static Strategy mapRecord(CSVRecord record) {
        return Strategy.builder()
                .strategyId(Utils.csvStringRecordToInteger(record, STRATEGY_ID))
                .bondsPercentage(Utils.csvStringRecordToInteger(record, BONDS_PERCENTAGE))
                .cashPercentage(Utils.csvStringRecordToInteger(record, CASH_PERCENTAGE))
                .maxRiskLevel(Utils.csvStringRecordToInteger(record, MAX_RISK_LEVEL))
                .minRiskLevel(Utils.csvStringRecordToInteger(record, MIN_RISK_LEVEL))
                .maxYearsToRetirement(Utils.csvStringRecordToInteger(record, MAX_YEARS_TO_RETIREMENT))
                .minYearsToRetirement(Utils.csvStringRecordToInteger(record, MIN_YEARS_TO_RETIREMENT))
                .stocksPercentage(Utils.csvStringRecordToInteger(record, STOCKS_PERCENTAGE))
                .build();
    }

}

package com.bcgdv.wealthmgmt.service;

import com.bcgdv.wealthmgmt.data.mapper.CustomerMapper;
import com.bcgdv.wealthmgmt.data.mapper.StrategyMapper;
import com.bcgdv.wealthmgmt.data.parser.AbstractCsvParser;
import com.bcgdv.wealthmgmt.model.Customer;
import com.bcgdv.wealthmgmt.model.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ParserService {

    @Autowired
    private AbstractCsvParser parser;
    private final static String CUSTOMER_CSV = "customers.csv";
    private final static String STRATEGIES_CSV = "strategies.csv";

    public List<Customer> getCustomers() {
        return parser.readCsvFile(
                CUSTOMER_CSV,
                CustomerMapper.FILE_HEADER_MAPPING,
                CustomerMapper::mapRecord);
    }

    public List<Strategy> getStrategies() {
        return parser.readCsvFile(
                STRATEGIES_CSV,
                StrategyMapper.FILE_HEADER_MAPPING,
                StrategyMapper::mapRecord);
    }

}

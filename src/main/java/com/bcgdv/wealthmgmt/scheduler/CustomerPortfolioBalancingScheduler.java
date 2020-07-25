package com.bcgdv.wealthmgmt.scheduler;

import com.bcgdv.wealthmgmt.common.Utils;
import com.bcgdv.wealthmgmt.config.FpsPropertiesConfig;
import com.bcgdv.wealthmgmt.gateway.FinanicalPotfolioServiceGateway;
import com.bcgdv.wealthmgmt.gateway.model.CustomerFinancialPortfolio;
import com.bcgdv.wealthmgmt.model.Customer;
import com.bcgdv.wealthmgmt.model.Strategy;
import com.bcgdv.wealthmgmt.scheduler.vo.CustomerPortfolioVO;
import com.bcgdv.wealthmgmt.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerPortfolioBalancingScheduler {

    @Autowired
    private ParserService parserService;
    @Autowired
    private FinanicalPotfolioServiceGateway finanicalPotfolioServiceGateway;
    private final FpsPropertiesConfig fpsPropertiesConfig;

    @Scheduled(cron = "0 0 1 * * *") // Runs every day at 1 AM
    public void balancePortfolio() {
        log.info("Starting balancing of customers portfolio...");
        Map<Customer, Strategy> customerStrategyMap = mapStrategiesToCustomers();

        List<CustomerFinancialPortfolio> reBalancedPortfolio = rebalancePortfolioForCustomers(customerStrategyMap);
        log.info("Re-calculated customer portfolio {} ", reBalancedPortfolio);
        List<List<CustomerFinancialPortfolio>> batchBalancedPotfolios = Utils.partitionList(reBalancedPortfolio, fpsPropertiesConfig.getBatchSize());
        batchBalancedPotfolios.stream().forEach(finanicalPotfolioServiceGateway::executeTrades);

    }

    private Map<Customer, Strategy> mapStrategiesToCustomers() {

        Map<Customer, Strategy> customerStrategyMap = new HashMap();
        parserService.getCustomers().stream().forEach(customer -> {
            parserService.getStrategies().stream().forEach(strategy -> {
                if (strategy.isAssignableToCustomer(customer)) {
                    log.info("Strategy {} matched to customer {} ", strategy.getStrategyId(), customer.getCustomerId());
                    customerStrategyMap.put(customer, strategy);
                }
            });
        });

        return customerStrategyMap;
    }

    private List<CustomerFinancialPortfolio> rebalancePortfolioForCustomers(Map<Customer, Strategy> customerStrategyMap) {
        List<CustomerFinancialPortfolio> balancedCustomerPortfolioList = new ArrayList();
        customerStrategyMap.forEach((customer, strategy) -> {
            CustomerFinancialPortfolio existingCustomerFinancialPortfolio = finanicalPotfolioServiceGateway.getCustomerFinancialPortfolio(customer.getCustomerId());
            CustomerPortfolioVO customerPortfolioVO = new CustomerPortfolioVO(customer, strategy, existingCustomerFinancialPortfolio);
            CustomerFinancialPortfolio reBalancedPortfolio = customerPortfolioVO.balancePotfolioForCustomerAgainstStrategy();
            balancedCustomerPortfolioList.add(reBalancedPortfolio);
        });

        return balancedCustomerPortfolioList;
    }
}

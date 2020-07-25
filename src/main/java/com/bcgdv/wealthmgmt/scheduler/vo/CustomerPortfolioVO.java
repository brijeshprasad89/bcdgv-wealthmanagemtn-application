package com.bcgdv.wealthmgmt.scheduler.vo;

import com.bcgdv.wealthmgmt.common.Utils;
import com.bcgdv.wealthmgmt.gateway.model.CustomerFinancialPortfolio;
import com.bcgdv.wealthmgmt.model.Customer;
import com.bcgdv.wealthmgmt.model.Strategy;
import lombok.Value;

import java.math.BigDecimal;


@Value
public class CustomerPortfolioVO {

    private Customer customer;
    private Strategy strategy;
    private CustomerFinancialPortfolio customerFinancialPortfolio;

    /**
     * @return
     */
    public CustomerFinancialPortfolio balancePotfolioForCustomerAgainstStrategy() {

        Integer bondsPercentage = strategy.getBondsPercentage();
        Integer cashPercentage = strategy.getCashPercentage();
        Integer stocksPercentage = strategy.getStocksPercentage();

        BigDecimal bondsAmount = customerFinancialPortfolio.getBonds();
        BigDecimal cashAmount = customerFinancialPortfolio.getCash();
        BigDecimal stocksAmount = customerFinancialPortfolio.getStocks();
        BigDecimal totalAmount = bondsAmount.add(cashAmount).add(stocksAmount); // 6700 + 1200 + 400 = 8300

        BigDecimal bondsRebalancedAmount = Utils.toBigDecimal(bondsPercentage)
                .multiply(totalAmount)
                .divide(Utils.toBigDecimal(100));

        BigDecimal cashRebalancedAmount = Utils.toBigDecimal(cashPercentage)
                .multiply(totalAmount)
                .divide(Utils.toBigDecimal(100));

        BigDecimal stocksRebalancedAmount = Utils.toBigDecimal(stocksPercentage)
                .multiply(totalAmount)
                .divide(Utils.toBigDecimal(100));

        BigDecimal bondsAmountDiff = bondsRebalancedAmount.subtract(bondsAmount);
        BigDecimal cashAmountDiff = cashRebalancedAmount.subtract(cashAmount);
        BigDecimal stocksAmountDiff = stocksRebalancedAmount.subtract(stocksAmount);

        return CustomerFinancialPortfolio.builder()
                .bonds(bondsAmountDiff)
                .cash(cashAmountDiff)
                .stocks(stocksAmountDiff)
                .customerId(customerFinancialPortfolio.getCustomerId())
                .build();

    }

}


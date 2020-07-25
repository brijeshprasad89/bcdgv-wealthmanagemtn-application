package com.bcgdv.wealthmgmt.gateway;

import com.bcgdv.wealthmgmt.config.FpsPropertiesConfig;
import com.bcgdv.wealthmgmt.gateway.model.CustomerFinancialPortfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FinanicalPotfolioServiceGateway {

    @Autowired
    private RestTemplate restTemplate;
    private final FpsPropertiesConfig fpsPropertiesConfig;

    public CustomerFinancialPortfolio getCustomerFinancialPortfolio(Integer customerId) {
        try {

            String url = fpsPropertiesConfig.getHost() + fpsPropertiesConfig.getCustomerEndPoint() + customerId;
            ResponseEntity<CustomerFinancialPortfolio> exchange = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, CustomerFinancialPortfolio.class);
            return exchange.getBody();
        } catch (Exception ex) {
            log.error("Error while connecting to Finance porfolio service ", ex.getMessage());
            throw ex;
        }
    }

    public ResponseEntity executeTrades(List<CustomerFinancialPortfolio> customerFinancialPortfolios) {
        log.info("Executing batch {} ", customerFinancialPortfolios);
        String url = fpsPropertiesConfig.getHost() + fpsPropertiesConfig.getExecuteEndPoint();
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(customerFinancialPortfolios), String.class);
            return ResponseEntity.status(HttpStatus.CREATED).
                    build();
        } catch (Exception ex) {
            log.error("Error while executing trades ", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}

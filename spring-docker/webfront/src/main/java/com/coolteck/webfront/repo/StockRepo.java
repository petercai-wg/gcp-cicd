package com.coolteck.webfront.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.coolteck.webfront.services.dto.StockDTO;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StockRepo {

    private static final Logger log = LoggerFactory.getLogger(StockRepo.class);

    @Value("${stockManagerUri}")
    private String stockManagerUri;

    @Autowired
    @Qualifier(value = "stdRestTemplate")
    private RestTemplate restTemplate;

    public Map<String, StockDTO> getStockDTOs() {
        log.info("getStocksDTOs All ...");
        ResponseEntity<List<StockDTO>> stockManagerResponse = restTemplate.exchange(stockManagerUri + "/stocks",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<StockDTO>>() {
                });
        List<StockDTO> stockDTOs = stockManagerResponse.getBody();

        if (stockDTOs == null) {
            return Map.of();
        }

        return stockDTOs.stream()
                .collect(Collectors.toMap(StockDTO::productId, Function.identity()));
    }

    public Map<String, StockDTO> stocksNotFound() {
        log.info("stocksNotFound *** FALLBACK ***");
        return Map.of();
    }

    public StockDTO getStockDTObyID(String productId) {
        log.info("getStockDTObyID: " + productId);

        ResponseEntity<StockDTO> stockManagerResponse = restTemplate.exchange(
                stockManagerUri + "/stocks/" + productId,
                HttpMethod.GET, null, new ParameterizedTypeReference<StockDTO>() {
                });

        StockDTO stockDTOs = stockManagerResponse.getBody();
        log.info("getStockDTObyID: " + productId + " returned " + stockDTOs);
        return stockDTOs;
    }

}

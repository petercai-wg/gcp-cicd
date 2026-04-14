package com.coolteck.stockmanager.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.coolteck.stockmanager.exceptions.StockNotFoundException;
import com.coolteck.stockmanager.model.Stock;
import com.coolteck.stockmanager.services.StockService;

import java.net.InetAddress;
import java.util.List;

@RestController
public class StockResource {

    private static final Logger log = LoggerFactory.getLogger(StockResource.class);

    @Autowired
    private StockService stockService;

    @GetMapping("/host")
    public String getHostName() {

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String hostAddress = localHost.getHostAddress();
            return hostName + " (" + hostAddress + ")";
        } catch (Exception e) {
            return "Unable to get hostname: " + e.getMessage();
        }
    }

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "OK \n";
    }

    /*
     * Some Load Balancer health check will use / as the health check endpoint.
     */
    @GetMapping("/")
    public String healthResponse() {
        log.info("StockManager  / called");
        return "OK";
    }

    @RequestMapping("/stocks")
    public List<Stock> getStocks() {
        return stockService.getStocks();
    }

    @RequestMapping("/stocks/{productId}")
    public Stock getStock(@PathVariable("productId") String productId) throws StockNotFoundException {
        log.info("getStock with productId: {}", productId);
        return stockService.getStock(productId);
    }

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleStockNotFound(StockNotFoundException snfe) {
    }
}

package com.coolteck.stockmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.coolteck.stockmanager.model.Stock;
import com.coolteck.stockmanager.repositories.StockRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataGenerator {

    private static final Logger log = LoggerFactory.getLogger(DataGenerator.class);

    private StockRepository stockRepository;

    @Autowired
    protected DataGenerator(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Generating synthetic data for demonstration purposes...");

        stockRepository.save(new Stock("1", "12345678", 5));
        stockRepository.save(new Stock("2", "34567890", 2));
        stockRepository.save(new Stock("3", "54326745", 9));
        stockRepository.save(new Stock("4", "93847614", 0));
        stockRepository.save(new Stock("5", "11856388", 1));

        log.info("... data generation complete");
    }
}

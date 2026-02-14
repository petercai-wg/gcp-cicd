package com.coolteck.stockmanager.repositories;

import org.springframework.data.repository.ListCrudRepository;
import com.coolteck.stockmanager.model.Stock;

public interface StockRepository extends ListCrudRepository<Stock, String> {
}

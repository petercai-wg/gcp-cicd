package com.coolteck.webfront.services.dto;

public record StockDTO(String productId, String sku, int amountAvailable) {
    public static final StockDTO DEFAULT_STOCK_DTO = new StockDTO("", "default", 999);
}

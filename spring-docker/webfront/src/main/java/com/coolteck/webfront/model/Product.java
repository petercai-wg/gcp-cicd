package com.coolteck.webfront.model;

import java.math.BigDecimal;

public record Product(String id, String sku, String name, String description, BigDecimal price, int amountAvailable,
        String location) {
}

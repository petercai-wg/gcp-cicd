package com.coolteck.productcatalogue.services;

import com.coolteck.productcatalogue.model.Product;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.*;
import java.net.InetAddress;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private Map<String, Product> productDAO = new HashMap<>();

    public ProductService() {
        productDAO.put("1", new Product("1", "Widget", "Premium ACME Widgets", new BigDecimal("1.20")));
        productDAO.put("2", new Product("2", "Sprocket", "Grade B sprockets", new BigDecimal("4.10")));
        productDAO.put("3", new Product("3", "Anvil", "Large Anvils", new BigDecimal("45.50")));
        productDAO.put("4", new Product("4", "Cogs", "Grade Y cogs", new BigDecimal("1.80")));
        productDAO.put("5", new Product("5", "Multitool", "Multitools", new BigDecimal("154.10")));
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String hostAddress = localHost.getHostAddress();
            productDAO.values().forEach(product -> product.setLocation(hostName + " (" + hostAddress + ")"));
        } catch (Exception e) {
            log.warn("Unable to set product location: " + e.getMessage());
        }

    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productDAO.values());
    }

    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(productDAO.get(id));
    }
}

package com.coolteck.productcatalogue.services;

import com.coolteck.productcatalogue.model.Product;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.*;
import java.net.InetAddress;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    Tracer tracer = GlobalOpenTelemetry.getTracer("ProductCatalogService", "1.0");

    private Map<String, Product> productDAO = new HashMap<>();

    public ProductService() {
        productDAO.put("1", new Product("1", "Widget", "Premium ACME Widgets", new BigDecimal("1.20")));
        productDAO.put("2", new Product("2", "Sprocket", "Grade B sprockets", new BigDecimal("4.10")));
        productDAO.put("3", new Product("3", "Anvil", "Large Anvils", new BigDecimal("45.50")));
        productDAO.put("4", new Product("4", "Cogs", "Grade Y cogs", new BigDecimal("1.80")));
        productDAO.put("5", new Product("5", "Multitool", "Multitools", new BigDecimal("154.10")));
        String hostInfo = getHostname();
        for (Product product : productDAO.values()) {
            product.setLocation(hostInfo);
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productDAO.values());
    }

    public Optional<Product> getProduct(String id) {

        Span parent = tracer.spanBuilder("process-getProduct").startSpan();

        try (Scope scope = parent.makeCurrent()) {
            log.info("get Hostname ");
            String hostInfo = getHostname();

            log.info("Fetching product with ID: {} from host: {}", id, hostInfo);
            Product product = productDAO.get(id);
            if (product != null) {
                product.setLocation(hostInfo);
            }

            return Optional.ofNullable(product);

        } finally {
            parent.end();
        }

    }

    public String getHostname() {

        Span span = tracer.spanBuilder("charge-payment").startSpan();

        try (Scope scope = span.makeCurrent()) {

            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));
                InetAddress localHost = InetAddress.getLocalHost();
                String hostName = localHost.getHostName();
                String hostAddress = localHost.getHostAddress();
                return hostName + " (" + hostAddress + ")";
            } catch (Exception e) {
                return "Unable to get hostname: " + e.getMessage();
            }

        } finally {
            span.end();
        }

    }

}

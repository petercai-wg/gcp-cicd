package com.coolteck.productcatalogue.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coolteck.productcatalogue.model.Product;
import com.coolteck.productcatalogue.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.List;

@RestController
public class ProductResource {
    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
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
        log.info("ProductCatalogue  / called");
        return "OK";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Retrieving all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        log.info("Retrieving product with id: {}", id);
        return productService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/host")
    public String getHostName() {
        log.info("getHostName");
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String hostAddress = localHost.getHostAddress();
            return hostName + " (" + hostAddress + ")";
        } catch (Exception e) {
            return "Unable to get hostname: " + e.getMessage();
        }
    }
}

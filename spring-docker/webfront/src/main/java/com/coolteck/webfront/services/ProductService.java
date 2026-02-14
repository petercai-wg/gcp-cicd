package com.coolteck.webfront.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolteck.webfront.model.Product;
import com.coolteck.webfront.repo.StockRepo;
import com.coolteck.webfront.repo.ProductRepo;
import com.coolteck.webfront.services.dto.ProductDTO;
import com.coolteck.webfront.services.dto.StockDTO;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private ProductRepo productRepo;

    public List<Product> listProduct() {
        Map<String, ProductDTO> productDTOs = productRepo.getProductDTOs();
        return productDTOs.values().stream().map(p -> {
            StockDTO stockDTO = stockRepo.getStockDTObyID(p.id());
            if (stockDTO == null) {
                stockDTO = StockDTO.DEFAULT_STOCK_DTO;
            }
            return new Product(p.id(), stockDTO.sku(), p.name(), p.description(), p.price(),
                    stockDTO.amountAvailable(), p.location());
        }).toList();
    }

    public List<Product> getProducts() {
        Map<String, ProductDTO> productDTOs = productRepo.getProductDTOs();
        Map<String, StockDTO> stockDTOMap = stockRepo.getStockDTOs();

        // Merge productDTOs and stockDTOs to a List of Products
        return productDTOs.values().stream()
                .map(productDTO -> {
                    StockDTO stockDTO = stockDTOMap.get(productDTO.id());
                    if (stockDTO == null) {
                        stockDTO = StockDTO.DEFAULT_STOCK_DTO;
                    }
                    return new Product(productDTO.id(), stockDTO.sku(), productDTO.name(),
                            productDTO.description(), productDTO.price(), stockDTO.amountAvailable(),
                            productDTO.location());
                })
                .toList();
    }

    public List<Product> productsNotFound() {
        return List.of();
    }

    public String getHostname() {
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

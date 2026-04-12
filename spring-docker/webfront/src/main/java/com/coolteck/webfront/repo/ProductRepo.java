package com.coolteck.webfront.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.coolteck.webfront.services.dto.ProductDTO;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductRepo {

    @Value("${productCatalogueUri}")
    private String productCatalogueUri;

    @Autowired
    @Qualifier(value = "stdRestTemplate")
    private RestTemplate restTemplate;

    public Map<String, ProductDTO> getProductDTOs() {
        ResponseEntity<List<ProductDTO>> productCatalogueResponse = restTemplate.exchange(
                productCatalogueUri + "/products",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {
                });
        List<ProductDTO> productDTOs = productCatalogueResponse.getBody();
        log.info("Fetched {} products from product catalogue", productDTOs.size());

        return productDTOs.stream()
                .collect(Collectors.toMap(ProductDTO::id, Function.identity()));
    }
}

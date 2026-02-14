package com.coolteck.webfront.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceResource {

    @Bean(name = "stdRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

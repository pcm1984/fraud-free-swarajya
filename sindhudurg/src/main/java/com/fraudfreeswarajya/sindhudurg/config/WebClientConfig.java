package com.fraudfreeswarajya.sindhudurg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient tanajiWebClient() {
        return WebClient.builder()
                .baseUrl("http://tanaji-api:8000")  // Or Docker URL later
                .build();
    }
}

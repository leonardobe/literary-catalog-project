package br.com.literarycatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String GUTENDEX_URL = "https://gutendex.com/books/";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(GUTENDEX_URL).build();
    }
}

package br.com.literarycatalog.client;

import br.com.literarycatalog.dto.GutendexDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class LiteraryCatalogClient {

    private final WebClient webClient;

    public LiteraryCatalogClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GutendexDTO> searchByTitle(String title) {
        try {
            return webClient
                    .get()
                    .uri(u -> u.queryParam("search", title).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException(
                                    "Failed to fetch book from Gutendex API. HTTP Status: " + r.statusCode()))))
                    .bodyToMono(GutendexDTO.class);
        } catch (WebClientResponseException ex) {
            throw new RuntimeException(
                    "Gutendex API returned status " + ex.getStatusCode() + " while retrieving books by name.");
        } catch (WebClientRequestException ex) {
            throw new RuntimeException(
                    "Connectivity error when accessing the Gutendex API to retrieve books by name. Check your"
                            + " network.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while searching for books by name: " + e.getMessage());
        }
    }

    public Mono<GutendexDTO> searchByAuthor(String author) {
        try {
            return webClient
                    .get()
                    .uri(u -> u.queryParam("search", author).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException(
                                    "Failed to fetch book from Gutendex API. HTTP Status: " + r.statusCode()))))
                    .bodyToMono(GutendexDTO.class);
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("Gutendex API responded with error " + ex.getStatusCode()
                    + " while retrieving books by the author entered.");
        } catch (WebClientRequestException ex) {
            throw new RuntimeException(
                    "Connectivity error when accessing the Gutendex API to retrieve books by the author entered. Check"
                            + " your network.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while searching for author: " + e.getMessage());
        }
    }

    public Mono<GutendexDTO> listTop10() {
        try {
            return webClient
                    .get()
                    .uri(u -> u.queryParam("sort", "popular").build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException(
                                    "Failed to fetch book from Gutendex API. HTTP Status: " + r.statusCode()))))
                    .bodyToMono(GutendexDTO.class);
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("Gutendex API responded with error " + ex.getStatusCode()
                    + " while retrieving top 10 downloaded books.");
        } catch (WebClientRequestException ex) {
            throw new RuntimeException(
                    "Connectivity error while accessing Gutendex API to retrieve top 10 downloaded books. Please check"
                            + " your network.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while searching for books: " + e.getMessage());
        }
    }
}

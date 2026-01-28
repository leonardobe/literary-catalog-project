package br.com.literarycatalog.service;

import br.com.literarycatalog.client.LiteraryCatalogClient;
import br.com.literarycatalog.dto.GutendexDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraryCatalogService {

    private final LiteraryCatalogClient client;

    public LiteraryCatalogService(LiteraryCatalogClient client) {
        this.client = client;
    }

    public List<GutendexDTO.BookDTO> searchByTitle(String title) {
        GutendexDTO response = client.searchByTitle(title).block();

        return response != null ? response.books() : List.of();
    }

    public List<GutendexDTO.BookDTO> searchByAuthor(String author) {
        GutendexDTO response = client.searchByAuthor(author).block();

        return response != null ? response.books() : List.of();
    }

    public List<GutendexDTO.BookDTO> searchByTitleAndAuthor(String title, String author) {
        GutendexDTO response = client.searchByTitle(author + " " + title).block();

        return response != null ? response.books() : List.of();
    }

    public List<GutendexDTO.BookDTO> top10MostDownloadedBooks() {
        GutendexDTO response = client.listTop10().block();

        return response != null ? response.books().stream().limit(10).toList() : List.of();
    }

}

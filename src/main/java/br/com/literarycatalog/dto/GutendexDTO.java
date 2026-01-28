package br.com.literarycatalog.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GutendexDTO(
        @JsonAlias("count") Integer count,
        @JsonAlias("results") List<BookDTO> books) {
    public record BookDTO(
            Long id,
            String title,
            List<AuthorDTO> authors,
            List<String> summaries,
            List<String> subjects,
            List<String> languages,
            @JsonProperty("download_count") Integer downloadCount) {}

    public record AuthorDTO(
            String name,
            @JsonProperty("birth_year") Integer birthYear,
            @JsonProperty("death_year") Integer deathYear) {}
}

package br.com.literarycatalog.mapper;

import br.com.literarycatalog.domain.entity.Author;
import br.com.literarycatalog.domain.entity.Book;
import br.com.literarycatalog.dto.GutendexDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(GutendexDTO.BookDTO dto) {
        Book book = new Book();
        book.setGutendexId(dto.id());
        book.setTitle(dto.title());
        book.setDownloadCount(dto.downloadCount());

        if (dto.languages() != null) {
            book.setLanguages(dto.languages());
        }

        if (dto.subjects() != null) {
            book.setSubjects(dto.subjects());
        }

        if (dto.summaries() != null) {
            book.setSummary(dto.summaries());
        }

        if (dto.authors() != null) {
            dto.authors().forEach(apiAuthor -> {
                String prettyName = toPrettyString(apiAuthor.name());

                Author author = new Author();
                author.setName(prettyName);
                author.setBirthYear(apiAuthor.birthYear());
                author.setDeathYear(apiAuthor.deathYear());

                book.addAuthor(author);
            });
        }
        return book;
    }

    private String toPrettyString(String rawName) {
        if (rawName == null || rawName.isBlank()) {
            return rawName;
        }

        String name = rawName.trim();

        if (name.contains(",")) {
            String[] parts = name.split(",", 2);
            String lastName = parts[0].trim();
            String firstNames = parts[1].trim();
            name = firstNames + " " + lastName;
        }

        name = name.replaceAll("\\s+", " ");

        return name.trim();
    }
}

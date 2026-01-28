package br.com.literarycatalog.service;

import br.com.literarycatalog.domain.entity.Author;
import br.com.literarycatalog.domain.entity.Book;
import br.com.literarycatalog.dto.GutendexDTO;
import br.com.literarycatalog.mapper.BookMapper;
import br.com.literarycatalog.repository.AuthorRepository;
import br.com.literarycatalog.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper,
            AuthorRepository authorRepository,
            AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
    }

    private String normalize(String input) {
        if (input == null) {
            return null;
        }

        String value = input.trim().replaceAll("\\s+", " ");

        if (value.contains(",")) {
            String[] parts = value.split(",", 2);
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();
            value = firstName + " " + lastName;
        }

        return value;
    }

    @Transactional
    public Book saveBook(GutendexDTO.BookDTO dto) {

        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("Book name is required");
        }

        Book book = bookMapper.toEntity(dto);

        List<String> authorNames =
                book.getAuthors().stream().map(Author::getName).toList();

        boolean exists = findByTitleAndAuthor(book.getTitle(), authorNames);

        if (exists) {
            throw new IllegalStateException("Book already exists for one of the authors: " + book.getTitle());
        }

        List<Author> persistentAuthors = book.getAuthors().stream()
                .map(author -> authorService
                        .findAuthorByName(author.getName())
                        .orElseGet(() -> authorService.saveAuthor(author)))
                .toList();

        book.setAuthors(persistentAuthors);

        return bookRepository.save(book);
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> listByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public boolean findByTitleAndAuthor(String title, String author) {

        String normalizedAuthor = normalize(author);

        return bookRepository.findByAuthorAndTitleName(title, List.of(normalizedAuthor));
    }

    public boolean findByTitleAndAuthor(String title, List<String> authors) {

        List<String> normalizedAuthors =
                authors.stream().map(this::normalize).map(String::toLowerCase).toList();

        return bookRepository.findByAuthorAndTitleName(title, normalizedAuthors);
    }
}

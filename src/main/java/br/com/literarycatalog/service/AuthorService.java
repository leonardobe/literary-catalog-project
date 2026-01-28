package br.com.literarycatalog.service;

import br.com.literarycatalog.domain.entity.Author;
import br.com.literarycatalog.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(Author author) {
        if (author.getName().isBlank() || author.getName() == null) {
            throw new IllegalArgumentException("Author name is required");
        }

        return authorRepository.save(author);
    }

    public Optional<Author> findAuthorByName(String name) {

        return authorRepository.findByNameIgnoreCase(name);
    }

    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }
}

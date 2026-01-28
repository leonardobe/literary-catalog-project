package br.com.literarycatalog.controller;

import br.com.literarycatalog.console.ApiResultHandler;
import br.com.literarycatalog.console.ConsoleUI;
import br.com.literarycatalog.domain.entity.Author;
import br.com.literarycatalog.domain.entity.Book;
import br.com.literarycatalog.dto.GutendexDTO;
import br.com.literarycatalog.service.AuthorService;
import br.com.literarycatalog.service.BookService;
import br.com.literarycatalog.service.LiteraryCatalogService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LiteraryCatalogController {

    private final LiteraryCatalogService service;
    private final BookService bookService;
    private final AuthorService authorService;
    private final ConsoleUI ui;
    private final ApiResultHandler resultHandler;

    public LiteraryCatalogController(
            LiteraryCatalogService service,
            BookService bookService,
            AuthorService authorService,
            ConsoleUI ui,
            ApiResultHandler resultHandler) {
        this.service = service;
        this.bookService = bookService;
        this.authorService = authorService;
        this.ui = ui;
        this.resultHandler = resultHandler;
    }

    public void searchBookByTitle() {
        String title = ui.ask("Enter book title: ");

        Optional<Book> existingBook = bookService.findByTitle(title);

        if (existingBook.isPresent()) {
            System.out.println("\n⚠\uFE0F Book already registered.");
            return;
        }

        List<GutendexDTO.BookDTO> results = service.searchByTitle(title);

        if (results.isEmpty()) {
            System.out.println("\n⚠\uFE0F Book not found.");
            return;
        }

        resultHandler.handle(results);
    }

    public void searchBookByAuthor() {
        String author = ui.ask("Enter author name: ");

        List<GutendexDTO.BookDTO> results = service.searchByAuthor(author);

        resultHandler.handle(results);
    }

    public void searchBookByTitleAndAuthor() {
        String title = ui.ask("Enter book title: ");
        String author = ui.ask("Enter author name: ");

        boolean existingBook = bookService.findByTitleAndAuthor(title, author);

        if (existingBook) {
            System.out.println("\n⚠\uFE0F Book already registered.");
            return;
        }

        List<GutendexDTO.BookDTO> results = service.searchByTitleAndAuthor(title, author);

        if (results.isEmpty()) {
            System.out.println("\n⚠\uFE0F Book not found.");
            return;
        }

        resultHandler.handle(results);
    }

    public void listTop10() {
        List<GutendexDTO.BookDTO> results = service.top10MostDownloadedBooks();

        resultHandler.handle(results);
    }

    @Transactional
    public void listSavedBooks() {
        List<Book> books = bookService.listAllBooks();

        if (books.isEmpty()) {
            ui.showMessage("\n❌ No books found");
            return;
        }

        books.forEach(ui::printBook);
    }

    @Transactional
    public void listSavedAuthors() {
        List<Author> authors = authorService.listAllAuthors();

        if (authors.isEmpty()) {
            ui.showMessage("\n❌ No authors found");
            return;
        }

        authors.forEach(ui::printAuthor);
    }

    @Transactional
    public void listBooksByLanguage() {
        String language = ui.ask("Enter language code (e.g., en, pt): ");

        List<Book> books = bookService.listByLanguage(language);

        if (books.isEmpty()) {
            ui.showMessage("\n❌ No books found for this language.");
            return;
        }

        books.forEach(ui::printBook);
    }
}

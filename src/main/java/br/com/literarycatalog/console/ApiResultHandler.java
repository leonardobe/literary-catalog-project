package br.com.literarycatalog.console;

import br.com.literarycatalog.dto.GutendexDTO;
import br.com.literarycatalog.service.BookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ApiResultHandler {

    private final ConsoleUI ui;
    private final BookService bookService;

    public ApiResultHandler(ConsoleUI ui, BookService bookService) {
        this.ui = ui;
        this.bookService = bookService;
    }

    public void handle(List<GutendexDTO.BookDTO> results) {
        if (results.isEmpty()) {
            ui.showMessage("\n❌ No results found.");
            return;
        }

        AtomicInteger counter = new AtomicInteger(1);

        results.forEach(r -> {
            String authorsName =
                    r.authors().stream().map(GutendexDTO.AuthorDTO::name).collect(Collectors.joining(", "));

            String breakLine = (counter.get() == 1) ? "\n" : "";

            System.out.println(breakLine + "(" + counter.getAndIncrement() + ") " + r.title() + " | Authors: "
                    + authorsName + " | Downloads: " + ConsoleUI.formatDownloadsCount(r.downloadCount()));
        });

        int choice;

        try {
            choice = Integer.parseInt(ui.ask("\n➡\uFE0F Choose a book number to save (0 to cancel): ")
                    .trim());
        } catch (NumberFormatException e) {
            ui.showMessage("\n❌ Invalid input. Cancelled.");
            return;
        }

        if (choice <= 0 || choice > results.size()) {
            ui.showMessage("\n❌ Cancelled.");
            return;
        }

        GutendexDTO.BookDTO selected = results.get(choice - 1);
        bookService.saveBook(selected);
        ui.showMessage("\n✅ Book saved  successfully.");
    }

}

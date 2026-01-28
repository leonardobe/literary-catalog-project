package br.com.literarycatalog.console;

import br.com.literarycatalog.domain.entity.Author;
import br.com.literarycatalog.domain.entity.Book;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsoleUI {

    private final Scanner sc = new Scanner(System.in);

    public static String formatDownloadsCount(int fansCount) {
        NumberFormat fmt = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        fmt.setMaximumFractionDigits(1);
        fmt.setMinimumFractionDigits(0);

        return fmt.format(fansCount);
    }

    public String ask(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void printBook(Book book) {
        System.out.println("\n\uD83D\uDCCC Book Information:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Downloads: " + ConsoleUI.formatDownloadsCount(book.getDownloadCount()));

        String language = String.join(", ", book.getLanguages());
        String langLabel = book.getLanguages().size() > 1 ? "Languages: " : "Language: ";

        System.out.println(langLabel + language);

        String authorName = book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", "));

        String label = book.getAuthors().size() > 1 ? "Authors: " : "Author: ";

        System.out.println(label + authorName);
        book.getSummary().forEach(System.out::println);
    }

    public void printAuthor(Author author) {
        System.out.println("\n\uD83D\uDCCC Author Information:");
        System.out.println("Name: " + author.getName());
        System.out.println("Born: " + author.getBirthYear());
        System.out.println("Died: " + author.getDeathYear());

        String authorName = author.getBooks().stream().map(Book::getTitle).collect(Collectors.joining(" | "));

        String label = author.getBooks().size() > 1 ? "Books: " : "Book: ";

        System.out.println(label + authorName);
    }
}

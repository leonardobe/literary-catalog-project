package br.com.literarycatalog.runner;

import br.com.literarycatalog.controller.LiteraryCatalogController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {
    private final LiteraryCatalogController controller;
    private final ConfigurableApplicationContext context;
    private final Scanner sc = new Scanner(System.in);

    public Menu(LiteraryCatalogController controller, ConfigurableApplicationContext context) {
        this.controller = controller;
        this.context = context;
    }

    public void start() {

        boolean running = true;

        while (running) {
            printMenu();

            try {
                int option = Integer.parseInt(sc.nextLine().trim());

                switch (option) {
                    case 1 -> controller.searchBookByTitle();
                    case 2 -> controller.searchBookByAuthor();
                    case 3 -> controller.searchBookByTitleAndAuthor();
                    case 4 -> controller.listTop10();
                    case 5 -> controller.listSavedBooks();
                    case 6 -> controller.listSavedAuthors();
                    case 7 -> controller.listBooksByLanguage();
                    case 0 -> {
                        System.out.println("\n\uD83D\uDC4B Exiting application. See you next time!");
                        running = false;
                    }
                    default -> System.out.println("\n⚠\uFE0F Invalid option. Please choose between 0 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n⚠️ Invalid input. Please enter a numeric option.");
            } catch (RuntimeException e) {
                System.out.println("\n🚨 " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n❌ Unexpected error. Please try again.");
            }
        }
        sc.close();
        context.close();
    }

    private void printMenu() {
        System.out.println("""

            ======================================
                       Literary Catalog
            ======================================
            1 - Search book by title
            2 - Search book by author
            3 - Search book by title and author
            4 - List top 10 most downloaded books
            5 - List saved books (Database)
            6 - List saved authors (Database)
            7 - List books by language (Database)
            0 - Exit
            """);
        System.out.print("Choose an option: ");
    }
}

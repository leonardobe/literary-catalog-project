package br.com.literarycatalog.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LiteraryCatalogRunner implements CommandLineRunner {

    private final Menu menu;

    public LiteraryCatalogRunner(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run(String... args) {
        menu.start();
    }
}

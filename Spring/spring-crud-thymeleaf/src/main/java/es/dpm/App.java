package es.dpm;

import es.dpm.model.Book;
import es.dpm.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        BookRepository repo = context.getBean(BookRepository.class);

        List<Book> books = List.of(
                new Book("Book1", "Author1", 1995, 12.54),
                new Book("Book2", "Author2", 1978, 51.21),
                new Book("Book3", "Author3", 2005, 23.45),
                new Book("Book4", "Author4", 2022, 21.22),
                new Book("Book5", "Author5", 2014, 9.99),
                new Book("Book6", "Author6", 1956, 5.76)
        );

        repo.saveAll(books);
    }
}


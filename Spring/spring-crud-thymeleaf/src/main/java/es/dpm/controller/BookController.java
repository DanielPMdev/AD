package es.dpm.controller;

import es.dpm.model.Book;
import es.dpm.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String findAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/books/{id}")
    public String findBooksByID(Model model, @PathVariable Long id) {
        if (bookRepository.findById(id).isPresent()) {
            model.addAttribute("book", bookRepository.findById(id).get());
            return "book-view";
        }
        return "book-view";
    }

    @GetMapping("/books/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @GetMapping("/books/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (bookRepository.existsById(id)){
            bookRepository.findById(id).ifPresent(b ->
                    model.addAttribute("book", b));
            return "book-form";
        } else {
            return "redirect:/books/new";
        }
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute Book book) {
        if (book.getId() != null){
            //Actualización
            bookRepository.findById(book.getId()).ifPresent(b -> {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setYear(book.getYear());
                b.setPrice(book.getPrice());
                bookRepository.save(b);
            });
        } else {
            //Creación
            bookRepository.save(book);
        }
        return "redirect:/books"; //Mover a la lista de libros cuando ya se haya creado
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBookById(@PathVariable Long id) {
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        return "redirect:/books";
    }

}

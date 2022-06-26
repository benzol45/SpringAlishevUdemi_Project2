package springbase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springbase.entity.Book;
import springbase.entity.validators.BookValidator;
import springbase.services.BooksService;
import springbase.services.LibraryService;
import springbase.services.PeopleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;
    private final LibraryService libraryService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator, LibraryService libraryService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
        this.libraryService = libraryService;
    }

    @GetMapping()
    public String getAllBooksPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page
            , @RequestParam(name = "books_per_page", required = false, defaultValue = "0") int perPage
            , @RequestParam(name = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear
            ,  Model model) {

        model.addAttribute("books", booksService.getAllWithParam(page,perPage,sortByYear));
        return "/books/all";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable("id") int id, Model model) {
        Optional<Book> optionalBook = booksService.getById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("person",libraryService.getPeopleByBookId(id));
            model.addAttribute("people",peopleService.getAll());
            model.addAttribute("book", optionalBook.get());
            return "/books/book";
        } else {
            return "redirect:/books";
        }
    }

    @GetMapping("/add")
    public String getAddBookPage(Model model) {
        model.addAttribute("book",new Book());
        return "/books/add";
    }

    @PostMapping("/add")
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/add";
        }

        booksService.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getBookEditPage(@PathVariable("id") int id, Model model) {
        Optional<Book> optionalBook = booksService.getById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("book", optionalBook.get());
            return "/books/bookEdit";
        } else {
            return "redirect:/books";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/bookEdit";
        }

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/makefree")
    public String pickupBook(@PathVariable("id") int id) {
        libraryService.makeBookFree(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/giveout")
    public String getoutBookToPerson(@PathVariable("id") int id, @RequestParam("person_id") int person_id) {
        libraryService.giveoutBookToPerson(id,person_id);
        return "redirect:/books/"+id;
    }

    @GetMapping("/search")
    public String getSearchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBooks(@RequestParam("titleFragment") String titleFragment, Model model) {
        model.addAttribute("books", booksService.findBooksByTitle(titleFragment));
        return "books/search";
    }

}

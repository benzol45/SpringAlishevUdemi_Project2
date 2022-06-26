package springbase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbase.entity.Book;
import springbase.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private final BookRepository bookRepository;

    @Autowired
    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getAllWithParam(int page, int perPage, boolean sortByYear) {
        if (perPage!=0) {
            if (sortByYear) {
                return bookRepository.findAll(PageRequest.of(page, perPage, Sort.by("year"))).getContent();
            } else {
                return bookRepository.findAll(PageRequest.of(page, perPage)).getContent();
            }
        } else {
            if (sortByYear) {
                return bookRepository.findAll(Sort.by("year"));
            } else {
                return bookRepository.findAll();
            }
        }
    }

    @Transactional(readOnly = true)
    public Optional<Book> getById(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void add(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByTitle(String titleFragment) {
        return bookRepository.findByTitleStartingWithIgnoreCase(titleFragment);
    }
}

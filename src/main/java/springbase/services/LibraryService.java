package springbase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbase.entity.Book;
import springbase.entity.People;
import springbase.repository.LibraryRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPeopleId(int id) {
        List<Book> bookList = libraryRepository.getBooksByPeopleId(id);
        bookList.forEach(book->book.setExpired(checkExpired(book)));
        return bookList;
    }

    private boolean checkExpired(Book book) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(book.getDateOfGiven());
        calendar.add(Calendar.DAY_OF_YEAR,10);
        Date expitedDate = calendar.getTime();

        return new Date().after(expitedDate);
    }

    @Transactional(readOnly = true)
    public Optional<People> getPeopleByBookId(int id) {
        return libraryRepository.getPeopleByBookId(id);
    }

    @Transactional
    public void makeBookFree(int book_id) {
        libraryRepository.makeFreeBookById(book_id);
    }

    @Transactional
    public void giveoutBookToPerson(int book_id, int person_id) {
        libraryRepository.giveoutBookByIdToPersonById(book_id, person_id, new Date());
    }

}

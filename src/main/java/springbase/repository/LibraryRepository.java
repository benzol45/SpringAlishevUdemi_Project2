package springbase.repository;

import org.springframework.stereotype.Repository;
import springbase.entity.Book;
import springbase.entity.People;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class LibraryRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Book> getBooksByPeopleId(int id) {
        List<Book> result = em.createQuery("select book from Book as book where book.person.id=?1").setParameter(1,id).getResultList();
        return  result;
    }

    public Optional<People> getPeopleByBookId(int id) {
        Optional<People> result = em.createQuery("select book.person from Book as book where book.id=?1").setParameter(1,id).getResultStream().findFirst();
        return result;
    }

    public void makeFreeBookById(int book_id) {
        Book book = em.find(Book.class, book_id);
        People person = book.getPerson();
        person.getBooks().remove(book);
        book.setPerson(null);
        book.setDateOfGiven(null);
    }

    public void giveoutBookByIdToPersonById(int book_id, int person_id, Date dateOfGiven) {
        People person = em.find(People.class, person_id);
        Book book = em.find(Book.class, book_id);
        book.setPerson(person);
        person.getBooks().add(book);
        book.setDateOfGiven(dateOfGiven);
    }
}

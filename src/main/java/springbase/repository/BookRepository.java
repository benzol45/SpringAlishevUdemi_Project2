package springbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbase.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findFirstBookByAuthorAndTitleAndYear(String author, String title, int year);
    List<Book> findByTitleStartingWithIgnoreCase(String title);
}

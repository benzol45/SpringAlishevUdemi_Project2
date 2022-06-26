package springbase.entity.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springbase.entity.Book;
import springbase.repository.BookRepository;

@Component
public class BookValidator implements Validator {
    private final BookRepository bookRepository;

    @Autowired
    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookRepository.findFirstBookByAuthorAndTitleAndYear(book.getAuthor(), book.getTitle(), book.getYear()).isPresent()) {
            errors.rejectValue("author","","Такая книга уже есть в базе");
            errors.rejectValue("title","","Такая книга уже есть в базе");
            errors.rejectValue("year","","Такая книга уже есть в базе");
        }
    }
}

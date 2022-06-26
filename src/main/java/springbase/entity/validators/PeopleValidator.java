package springbase.entity.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springbase.entity.People;
import springbase.repository.PeopleRepository;

@Component
public class PeopleValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        People people = (People)target;
        if (peopleRepository.findFirstByNameIgnoreCase(people.getName()).isPresent()) {
            errors.rejectValue("name","","Человек с таким именем уже есть в базе");
        }
    }
}

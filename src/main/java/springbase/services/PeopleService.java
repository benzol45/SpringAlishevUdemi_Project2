package springbase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbase.entity.People;
import springbase.repository.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<People> getById(int id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    public void add(People people) {
        peopleRepository.save(people);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, People people) {
        people.setId(id);
        peopleRepository.save(people);
    }
}

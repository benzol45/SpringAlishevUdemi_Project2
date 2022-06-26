package springbase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springbase.entity.People;
import springbase.entity.validators.PeopleValidator;
import springbase.services.LibraryService;
import springbase.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PeopleValidator peopleValidator;
    private final LibraryService libraryService;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator, LibraryService libraryService) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
        this.libraryService = libraryService;
    }

    @GetMapping()
    public String getAllPeoplePage(Model model) {
        model.addAttribute("people", peopleService.getAll());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String getPersonPage(@PathVariable("id") int id, Model model) {
        Optional<People> optionalPeople = peopleService.getById(id);
        if (optionalPeople.isPresent()) {
            model.addAttribute("person", optionalPeople.get());
            model.addAttribute("books", libraryService.getBooksByPeopleId(id));
            return "people/person";
        } else {
            return "redirect:/people";
        }
    }

    @GetMapping("/add")
    public String getAddPersonPage(Model model) {
        model.addAttribute("person", new People());
        return "people/add";
    }

    @PostMapping("/add")
    public String addNewPerson(@ModelAttribute("person") @Valid People people, BindingResult bindingResult) {
        peopleValidator.validate(people, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/add";
        }

        peopleService.add(people);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPersonEditPage(@PathVariable("id") int id, Model model) {
        Optional<People> optionalPeople = peopleService.getById(id);
        if (optionalPeople.isPresent()) {
            model.addAttribute("person", optionalPeople.get());
            return "people/personEdit";
        } else {
            return "redirect:/people";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

    @PutMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/personEdit";
        }

        peopleService.update(id, people);
        return "redirect:/people";
    }
}
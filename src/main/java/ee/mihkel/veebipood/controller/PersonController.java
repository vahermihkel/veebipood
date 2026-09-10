package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.dto.LoginCredentials;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://veebipood-09-2026-frontend.onrender.com"})
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @PostMapping("persons")
    public Person savePerson(@RequestBody Person person) {
        if (person.getId() != null) {
            throw new RuntimeException("Cannot add person with id");
        }
        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if (dbPerson != null) {
            throw new RuntimeException("Email already in use");
        }
        return personRepository.save(person);
        //return personRepository.findAll();
    }

    @PostMapping("login")
    public Person login(@RequestBody LoginCredentials loginCredentials) {
        return personRepository.findByEmail(loginCredentials.email());
    }

    @DeleteMapping("persons/{id}")
    public List<Person> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

}
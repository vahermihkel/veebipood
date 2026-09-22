package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.dto.LoginCredentials;
import ee.mihkel.veebipood.dto.PersonDTO;
import ee.mihkel.veebipood.dto.PersonPublicDTO;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://veebipood-09-2026-frontend.onrender.com"})
public class PersonController {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @PostMapping("persons")
    public PersonDTO savePerson(@RequestBody Person person) {
        if (person.getId() != null) {
            throw new RuntimeException("Cannot add person with id");
        }
        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if (dbPerson != null) {
            throw new RuntimeException("Email already in use");
        }
        Person savedPerson = personRepository.save(person);
//        PersonDTO personDTO = new PersonDTO(
//                savedPerson.getId(),
//                person.getFirstName(),
//                person.getLastName(),
//                person.getEmail()
//        );
//        return personDTO;
        //return personRepository.findAll();
        return modelMapper.map(savedPerson, PersonDTO.class);
    }

    @PostMapping("login")
    public PersonDTO login(@RequestBody LoginCredentials loginCredentials) {
        Person dbPerson = personRepository.findByEmail(loginCredentials.email());
        return modelMapper.map(dbPerson, PersonDTO.class);
    }

    @CacheEvict(value = "persons", key = "#id")
    @DeleteMapping("persons/{id}")
    public List<Person> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

    @GetMapping("persons/public")
    public List<PersonPublicDTO> getPublicPersons() {
        List<Person> persons = personRepository.findAll();
        return List.of(modelMapper.map(persons, PersonPublicDTO[].class));
    }

    @Cacheable(value = "persons", key = "#id")
    @GetMapping("persons/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow();
    }


    @CachePut(value = "persons", key = "#person.id")
    @PutMapping("persons")
    public Person editPerson(@RequestBody Person person) {
        if (person.getId() == null) {
            throw new RuntimeException("Can't edit without ID");
        }
        return personRepository.save(person);
    }
}
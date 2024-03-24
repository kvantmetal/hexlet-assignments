package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> show(@PathVariable long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return ResponseEntity.of(optionalPerson);
    }

    // BEGIN
    @GetMapping("")
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
         personRepository.deleteById(id);
    }
    // END
}

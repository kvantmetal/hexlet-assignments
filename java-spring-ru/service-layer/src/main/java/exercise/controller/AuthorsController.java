package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping(path = "")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        List<AuthorDTO> allAutors = authorService.findAllAuthors();
        return ResponseEntity.ok(allAutors);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable long id) {
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        return ResponseEntity.ok(authorDTO);
    }

    @PostMapping(path = "")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorDTO authorDTO = authorService.save(authorCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable long id, @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        AuthorDTO authorDTO = authorService.update(id, authorUpdateDTO);
        return ResponseEntity.ok(authorDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable long id) {
        authorService.delete(id);
    }
    // END
}

package exercise.controller;

import java.util.List;

import exercise.dto.*;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping(path = "")
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        List<BookDTO> allBooks = bookService.findAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable long id) {
        BookDTO BookDTO = bookService.findBookById(id);
        return ResponseEntity.ok(BookDTO);
    }

    @PostMapping(path = "")
    public ResponseEntity<BookDTO> saveBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        BookDTO bookDTO = bookService.save(bookCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        BookDTO BookDTO = bookService.update(id, bookUpdateDTO);
        return ResponseEntity.ok(BookDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable long id) {
        bookService.delete(id);
    }
    // END
}

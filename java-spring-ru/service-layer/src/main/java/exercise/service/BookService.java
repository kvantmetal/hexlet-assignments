package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;

    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::map).toList();
    }

    public BookDTO findBookById(long id) {
        Book book = getBookById(id);
        return bookMapper.map(book);
    }

    public BookDTO save(BookCreateDTO bookCreateDTO) {
        Book book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO update(Long id, BookUpdateDTO bookUpdateDTO) {
        Book book = getBookById(id);
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    private Book getBookById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException("Book with id %d not found".formatted(id));
        }
        return optionalBook.get();
    }

    public void delete(long id) {
        bookRepository.deleteById(id);
    }
    // BEGIN

    // END
}

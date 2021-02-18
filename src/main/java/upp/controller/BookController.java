package upp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upp.converter.BookDTOToBookConverter;
import upp.dto.BookDTO;
import upp.model.Book;
import upp.service.BookService;
import upp.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDTOToBookConverter bookDTOToBookConverter;

    @RequestMapping(value = "/get-user/{userId}", method = RequestMethod.GET)
    public List<Book> getAll(@PathVariable Long userId) {
        return bookService.findAllByUser(userId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Book add(@RequestBody BookDTO bookDTO) {
        return bookService.save(bookDTOToBookConverter.convert(bookDTO));
    }
}

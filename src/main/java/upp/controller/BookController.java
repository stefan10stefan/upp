package upp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import upp.converter.BookDTOToBookConverter;
import upp.dto.BookDTO;
import upp.model.Book;
import upp.service.BookService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDTOToBookConverter bookDTOToBookConverter;

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public List<Book> getAll() {
        return bookService.findAllByUser();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Book add(@RequestBody BookDTO bookDTO) {
        return bookService.save(bookDTOToBookConverter.convert(bookDTO));
    }
}

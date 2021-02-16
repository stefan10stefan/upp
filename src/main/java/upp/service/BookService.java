package upp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.model.Book;
import upp.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    public List<Book> findAllByUser() {
        return bookRepository.findAllByUserId(userService.getCurrentUser().getId());
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}

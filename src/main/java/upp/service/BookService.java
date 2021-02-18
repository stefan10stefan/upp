package upp.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.model.Book;
import upp.model.User;
import upp.repository.BookRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    public List<Book> findAllByUser(Long userId) {
        return bookRepository.findAllByUserId(userId);
    }

    public Book save(Book book) {
        book.setUser(userService.getCurrentUser());

        book = bookRepository.save(book);

        List<Book> books = bookRepository.findAllByUserId(userService.getCurrentUser().getId());

        if(books.size() >= 2) {
            Task task = taskService.createTaskQuery().processInstanceId(book.getUser().getProcessId()).list().get(0);
            runtimeService.setVariable(task.getProcessInstanceId(),"documentCount", books.size());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("documentCount", books.size());
            formService.submitTaskForm(task.getId(), map);

            User user = book.getUser();
            user.setCanGrade(true);
            userService.save(user);
        }

        return book;
    }
}

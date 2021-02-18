package upp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import upp.model.Book;
import upp.model.Grade;
import upp.service.GradeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @RequestMapping(value = "/accept/{userId}", method = RequestMethod.GET)
    public void accept(@PathVariable Long userId) {
        gradeService.accept(userId);
    }

    @RequestMapping(value = "/decline/{userId}", method = RequestMethod.GET)
    public void decline(@PathVariable Long userId) {
        gradeService.decline(userId);
    }

    @RequestMapping(value = "/additional/{userId}", method = RequestMethod.GET)
    public void additional(@PathVariable Long userId) {
        gradeService.additional(userId);
    }

}

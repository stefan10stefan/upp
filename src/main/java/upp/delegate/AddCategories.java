package upp.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.converter.UserConverter;
import upp.dto.FormDTO;
import upp.service.UserService;

import java.util.List;

@Service
public class AddCategories implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String categories = (String) delegateExecution.getVariable("categories");
        Long userId = (Long) delegateExecution.getVariable("userId");

        userService.addCategories(userId, categories);

    }
}

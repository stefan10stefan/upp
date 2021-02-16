package upp.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.model.User;
import upp.service.UserService;

@Service
public class TokenLinkDelegate implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String token = (String) delegateExecution.getVariable("token");
        User user = userService.getUserByRegistrationToken(token);

        delegateExecution.setVariable("confirmed", user != null);
    }
}

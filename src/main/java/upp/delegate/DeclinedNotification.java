package upp.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import upp.model.User;
import upp.service.EmailService;
import upp.service.UserService;

@Service
public class DeclinedNotification implements JavaDelegate  {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");
        User user = userService.getUser(userId);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Declined notification");
        message.setTo(user.getEmail());
        message.setText("Declined notification");

        emailService.sendEmail(message);
    }

}

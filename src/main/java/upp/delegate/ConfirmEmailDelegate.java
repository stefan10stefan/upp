package upp.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import upp.dto.FormDTO;
import upp.model.User;
import upp.service.EmailService;
import upp.service.UserService;

import java.util.List;

@Service
public class ConfirmEmailDelegate implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long userId = (Long) delegateExecution.getVariable("userId");
        User user = userService.getUser(userId);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Confirm user account");
        message.setTo(user.getEmail());
        message.setText("http://localhost:8082/api/user/confirm/" + user.getRegistrationToken());

        emailService.sendEmail(message);
    }
}

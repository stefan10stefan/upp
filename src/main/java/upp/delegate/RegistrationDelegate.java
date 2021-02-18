package upp.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.converter.UserConverter;
import upp.dto.FormDTO;
import upp.model.User;
import upp.model.UserType;
import upp.service.UserService;

import java.util.List;

@Service
public class RegistrationDelegate implements JavaDelegate {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormDTO> dto = (List<FormDTO>) delegateExecution.getVariable("registerData");
        String processId = (String) delegateExecution.getVariable("processId");
        User user = userConverter.convert(dto);

        User dbUser = userService.getUserByEmail(user.getEmail());

        if(dbUser != null) {
            delegateExecution.setVariable("dataOK", false);
            delegateExecution.setVariable("userId", user.getId());
            return;
        }

        user.setUserType(UserType.WRITER);
        user.setProcessId(processId);
        user.setRegistrationToken(Long.toHexString(Double.doubleToLongBits(Math.random())));

        user = userService.save(user);

        delegateExecution.setVariable("dataOK", user != null);

        if(user != null) {
            delegateExecution.setVariable("userId", user.getId());
        }
    }
}

package upp.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import upp.converter.UserToUserDTOConverter;
import upp.dto.FormDTO;
import upp.dto.FormFieldDTO;
import upp.dto.UserDTO;
import upp.model.User;
import upp.service.UserService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserToUserDTOConverter userToUserDTOConverter;

    @CrossOrigin
    @RequestMapping(
            value = "/startProcess/{process}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<?> startProcess(@PathVariable String process) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(process);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new ResponseEntity<>(new FormFieldDTO(task.getId(), processInstance.getId(), properties),
                HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/registerUser/{taskId}",
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<?> register(@RequestBody List<FormDTO> dto, @PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormDTO pair:dto){
            map.put(pair.getFieldId(), pair.getFieldValue());
        }

        runtimeService.setVariable(task.getProcessInstanceId(), "registerData", dto);
        runtimeService.setVariable(task.getProcessInstanceId(), "taskId", taskId);

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/confirm/{token}/{taskId}",
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<?> confirmEmail(@PathVariable String token, @PathVariable String taskId) {
        User user = userService.confirmUser(token);
        if(user != null) {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            runtimeService.setVariable(task.getProcessInstanceId(),"confirmed", true);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/loginUser",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> login(@RequestBody List<FormDTO> dto) {

        User user = null;
        for(FormDTO field : dto){
            if(field.getFieldId().equals("email")){
                user = userService.getUserByEmail(field.getFieldValue());
            }
        }

        if(user !=null){

            if(!user.isEnabled()) {
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

            UserDTO dtoUser = userToUserDTOConverter.convert(user);
            for(FormDTO field : dto){
                if(field.getFieldId().equals("password")){
                    if(user.getPassword().equals(field.getFieldValue())){
                        userService.setCurrentUser(user);
                        return new ResponseEntity<>(dtoUser,HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(null,HttpStatus.OK);
                    }
                }
            }

        }else{
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getLoggedIn",
            method = RequestMethod.GET)
    public ResponseEntity<?> getLoggedIn() {
        User loggedIn = userService.getCurrentUser();
        if(loggedIn == null) {
            return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
        }
        UserDTO retVal = userToUserDTOConverter.convert(loggedIn);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST)
    public ResponseEntity<?> signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

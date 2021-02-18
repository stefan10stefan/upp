package upp.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.model.Grade;
import upp.model.GradeValue;
import upp.model.User;
import upp.model.UserType;
import upp.repository.GradeRepository;
import upp.repository.UserRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private UserService userService;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    public Grade accept(Long userId) {

        User admin = userService.getCurrentUser();
        User user = userService.getUser(userId);

        Grade grade = gradeRepository.findByWriterIdAndDirectorId(userId, admin.getId());

        if(grade == null) {
            grade = new Grade();
            grade.setGrade(GradeValue.APPROVED);
            grade.setDirector(admin);
            grade.setWriter(user);

            grade = gradeRepository.save(grade);
        }
        else {
            grade.setGrade(GradeValue.APPROVED);
            grade = gradeRepository.save(grade);
        }

        doCheck(user);

        return grade;
    }

    public Grade decline(Long userId) {

        User admin = userService.getCurrentUser();
        User user = userService.getUser(userId);

        Grade grade = gradeRepository.findByWriterIdAndDirectorId(userId, admin.getId());

        if(grade == null) {
            grade = new Grade();
            grade.setGrade(GradeValue.DECLINED);
            grade.setDirector(admin);
            grade.setWriter(user);

            grade = gradeRepository.save(grade);
        }
        else {
            grade.setGrade(GradeValue.APPROVED);
            grade = gradeRepository.save(grade);
        }

        doCheck(user);

        return grade;
    }

    public Grade additional(Long userId) {

        User admin = userService.getCurrentUser();
        User user = userService.getUser(userId);

        Grade grade = gradeRepository.findByWriterIdAndDirectorId(userId, admin.getId());

        if(grade == null) {
            grade = new Grade();
            grade.setGrade(GradeValue.NEED_MORE_INFORMATION);
            grade.setDirector(admin);
            grade.setWriter(user);

            grade = gradeRepository.save(grade);
        }
        else {
            grade.setGrade(GradeValue.APPROVED);
            grade = gradeRepository.save(grade);
        }

        doCheck(user);

        return grade;
    }


    private void doCheck(User user) {

        List<Grade> grades = gradeRepository.findAllByWriterId(user.getId());
        List<User> admins = userRepository.findAllByUserType(UserType.DIRECTOR);

        if(grades == null || grades.size() == 0) {
            return;
        }

        Task task = taskService.createTaskQuery().processInstanceId(user.getProcessId()).list().get(0);


        int declined = 0;

        for(Grade grade: grades) {

            if(grade.getGrade().equals(GradeValue.NEED_MORE_INFORMATION)) {

                runtimeService.setVariable(task.getProcessInstanceId(),"needAdditional", true);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("needAdditional", true);
                map.put("approved", false);
                map.put("declined", false);
                formService.submitTaskForm(task.getId(), map);
                return;
            }


            if(grade.getGrade().equals(GradeValue.DECLINED)) {

                declined++;
            }
        }

        if(grades.size() != admins.size()) {
            return;
        }

        if(declined > grades.size() / 2) {
            runtimeService.setVariable(task.getProcessInstanceId(),"needAdditional", false);
            runtimeService.setVariable(task.getProcessInstanceId(),"approved", false);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("needAdditional", "undefined");
            map.put("approved", false);
            map.put("declined", true);
            map.put("documentCount", 0);
            formService.submitTaskForm(task.getId(), map);
        }
        else {
            runtimeService.setVariable(task.getProcessInstanceId(),"needAdditional", false);
            runtimeService.setVariable(task.getProcessInstanceId(),"approved", true);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("needAdditional", "undefined");
            map.put("approved", true);
            map.put("declined", false);
            map.put("documentCount", 0);
            formService.submitTaskForm(task.getId(), map);
        }
    }
}

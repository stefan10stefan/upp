package upp.dto;

import org.camunda.bpm.engine.form.FormField;

import java.io.Serializable;
import java.util.List;

public class FormFieldDTO implements Serializable {

    String taskId;
    List<FormField> formFields;
    String processInstanceId;

    public FormFieldDTO(){ }

    public FormFieldDTO(String taskId, String processInstanceId,List<FormField> formFields) {
        super();
        this.taskId = taskId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}

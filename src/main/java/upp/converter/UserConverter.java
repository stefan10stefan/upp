package upp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import upp.dto.FormDTO;
import upp.model.User;

import java.util.List;

@Component
public class UserConverter implements Converter<List<FormDTO>, User> {

    @Override
    public User convert(List<FormDTO> formDTOS) {

        if(formDTOS == null){
            return null;
        }

        User user = new User();

        for(FormDTO dto: formDTOS) {

            if(dto.getFieldId().equals("firstName")) {
                user.setFirstName(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("lastName")) {
                user.setLastName(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("email")) {
                user.setEmail(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("password")) {
                user.setPassword(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("city")) {
                user.setCity(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("country")) {
                user.setCountry(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("movieCategory")) {
                user.setMovieCategory(dto.getFieldValue());
                continue;
            }
            if(dto.getFieldId().equals("isBeta")) {
                user.setBeta(Boolean.parseBoolean(dto.getFieldValue()));
                continue;
            }
        }

        return user;
    }
}

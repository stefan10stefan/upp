package upp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import upp.dto.FormDTO;
import upp.dto.UserDTO;
import upp.model.User;

import java.util.List;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setCity(user.getCity());
        userDTO.setCountry(user.getCountry());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMovieCategory(user.getMovieCategory());
        userDTO.setUserType(user.getUserType());
        userDTO.setId(user.getId());
        userDTO.setTaskId(user.getTaskId());

        return userDTO;

    }
}

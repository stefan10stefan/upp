package upp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import upp.model.User;
import upp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public User getUserByRegistrationToken(String token) { return userRepository.findByRegistrationToken(token); }
    public User getUserByEmail(String email) { return userRepository.findByEmail(email); }

    public void setCurrentUser(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Author"));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long id = Long.parseLong(auth.getName());
            Optional<User> user = userRepository.findById(id);
            User ret = user.orElseGet(null);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public User confirmUser(String token) {

        User user = userRepository.findByRegistrationToken(token);

        if(user == null) {
            return user;
        }

        user.setRegistrationToken(null);
        user.setEnabled(true);

        userRepository.save(user);
        return user;
    }
}

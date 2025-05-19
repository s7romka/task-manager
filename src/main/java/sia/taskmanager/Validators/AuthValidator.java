package sia.taskmanager.Validators;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.taskmanager.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   // public boolean validateLogin(String login) {
      //  log.info("Validating login: " + login);
        //return userRepository.findByLogin(login).isPresent();
    //}
    //public boolean validatePassword(String login, String password) {
        //log.info("Validating password: " + password);
        //return userRepository.findByLogin(login).map(user -> passwordEncoder.matches(password, user.getPassword())).orElse(false);
    //}
}

package sia.taskmanager.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sia.taskmanager.Validators.AuthValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthValidator authValidator;

   // public boolean userValid(String login, String password) {
        //log.info("Processing user's validation");
        //return authValidator.validateLogin(login) && authValidator.validatePassword(login, password);
    //}

}

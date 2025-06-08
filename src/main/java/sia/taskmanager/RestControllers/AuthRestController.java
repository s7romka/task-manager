package sia.taskmanager.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sia.taskmanager.DTO.AuthResponse;
import sia.taskmanager.DTO.UserRequest;
import sia.taskmanager.Forms.RegistrationForm;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.JwtService;
import sia.taskmanager.Services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthRestController(UserService userService,
                              PasswordEncoder passwordEncoder,
                              JwtService jwtService,
                              AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        if(userService.findByLogin(userRequest.getLogin()) != null) {
            return ResponseEntity.badRequest().body("Login already exists");
        }
        RegistrationForm form = new RegistrationForm();
        form.setLogin(userRequest.getLogin());
        form.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        form.setEmail(userRequest.getEmail());
        form.setUsername(userRequest.getLogin());
        userService.save(form.toUser(passwordEncoder));
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getLogin(), userRequest.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateJwtToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

package sia.taskmanager.Controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.taskmanager.Forms.RegistrationForm;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.UserService;
@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public String loadRegisterForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registerForm";
    }
    @PostMapping
    public String processRegisterForm(@Valid @ModelAttribute("registrationForm") RegistrationForm form, BindingResult bindingResult) {
        userService.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }



}

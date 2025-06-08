package sia.taskmanager.Controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sia.taskmanager.Forms.RegistrationForm;
import sia.taskmanager.Services.UserService;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder, @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String loadRegisterForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegisterForm(@Valid @ModelAttribute("registrationForm") RegistrationForm form) {
        userService.saveAndConfirm(form.toUser(passwordEncoder));
        return "redirect:/register/pending";
    }
    @GetMapping("/pending")
    public String loadPendingForm() {
        return "pending";
    }
    @GetMapping("/confirm")
    public String loadConfirmForm(@RequestParam("token") String token, Model model) {
        boolean confirmed = userService.confirm(token);
        if (confirmed) {
            return "redirect:/add";
        }
        else{
            model.addAttribute("error", "Invalid token");
            return "error";
        }
    }



}

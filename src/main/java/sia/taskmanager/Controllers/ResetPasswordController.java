package sia.taskmanager.Controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sia.taskmanager.DTO.PasswordResetDTO;
import sia.taskmanager.Models.User;
import sia.taskmanager.Repositories.UserRepository;
import sia.taskmanager.Services.UserService;

import java.util.Optional;
@Slf4j
@Controller
public class ResetPasswordController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public ResetPasswordController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("email", new String());
        return "forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@ModelAttribute("email") String email, Model model) {
        Optional<User> user = userService.findByEmail(email);
        userService.sendResetPasswordEmail(user);
        model.addAttribute("user", user);
        return "redirect:/pending";
    }
    @GetMapping("/pending")
    public String penging() {
        return "change-to-continue";
    }
    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        if (!userService.findByToken(token).isPresent()) {
            model.addAttribute("error", "Invalid or expired token");
            return "redirect:/forgot-password";
        }
        model.addAttribute("form", new PasswordResetDTO());
        model.addAttribute("token", token);
        return "reset-password";
    }
    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam("token") String token, @Valid @ModelAttribute("form") PasswordResetDTO password, Model model ,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("token", token);
            return "reset-password";
        }

        if (!password.getPassword().equals(password.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
            model.addAttribute("token", token);
            return "reset-password";
        }

        try {
            userService.resetPassword(token, passwordEncoder.encode(password.getPassword()));
            model.addAttribute("message", "Password changed successfully");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("token", token);
            return "reset-password";

        }
    }
}

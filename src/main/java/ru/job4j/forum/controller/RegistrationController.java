package ru.job4j.forum.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.exception.UserAlreadyExistException;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.UserService;

@Controller
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("registration")
    public String registrationForm(@RequestParam(value = "exist", required = false) boolean exist,
                                   Model model) {
        String errorMessage = null;
        if (exist) {
            errorMessage = "User with this name already exists";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "reg";
    }

    @PostMapping("registration")
    public String processRegistration(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.save(user);
        } catch (UserAlreadyExistException e) {
            return "redirect:/registration?exist=true";
        }
        return "redirect:/login";
    }
}

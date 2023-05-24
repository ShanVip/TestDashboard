package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.Role;
import com.project.testdashboard.entities.User;
import com.project.testdashboard.services.RoleService;
import com.project.testdashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
        return email.matches(regex);
    }


    @GetMapping("/register/user")
    public String registerPageUser(Principal principal) {
        return "register";
    }

    @GetMapping("/login")
    public String loginPageUser(Principal principal) {
        return "login";
    }



    @PostMapping("/register/user")
    public String registerUser(
            @ModelAttribute("user") User user,
            @RequestParam("password") String password,
            BindingResult result,
            Model model) {

        // проверка ошибок валидации
        if (result.hasErrors()) {
            // возвращаем ошибки в виде JSON-объекта
            model.addAttribute("errorMessage",result.getFieldErrors());
            return "register";
//            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        // Проверка пустых полей
        if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || password.isEmpty()) {
            model.addAttribute("errorMessage","Please fill in all fields!");
            return "register";
//            return ResponseEntity.badRequest().body("Please fill in all fields!");
        }

        // проверка формата email
        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("errorMessage","Invalid email format!");
            return "register";
//            return ResponseEntity.badRequest().body("Invalid email format!");
        }

        // проверка длины пароля
        if (password.length() < 6) {
            model.addAttribute("errorMessage","Password should be at least 6 characters long!");
            return "register";
//            return ResponseEntity.badRequest().body("Password should be at least 6 characters long!");
        }

        // проверка наличия пользователей с таким же логином или почтой
        if (userService.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            model.addAttribute("errorMessage","Username or email is already taken!");
            return "register";
//            return ResponseEntity.badRequest().body("Username or email is already taken!");
        }

        // проверка наличия ролей
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName("ROLE_USER");
        if (userRole != null) {
            roles.add(userRole);
        }

        if (user.getUsername().endsWith("admin")) {
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            if (adminRole != null) {
                roles.add(adminRole);
            }
        }

        // устанавливаем пароль
        user.setPassword(password);

        // сохраняем пользователя в базу данных
        userService.registerUser(user, roles);

        model.addAttribute("errorMessage","User registered successfully!");
        return "register";
//        return ResponseEntity.ok("User registered successfully!");
    }

}


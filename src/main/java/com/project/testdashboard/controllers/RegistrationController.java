package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.Role;
import com.project.testdashboard.entities.User;
import com.project.testdashboard.services.RoleService;
import com.project.testdashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping("/register/user")
    public String registerPageUser(Principal principal) {
        return "register user";
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User user, @RequestParam("password") String password, BindingResult result) {

        // проверка ошибок валидации
        if (result.hasErrors()) {
            // возвращаем ошибки в виде JSON-объекта
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        // проверка наличия пользователей с таким же логином или почтой
        if (userService.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            return ResponseEntity.badRequest().body("Username or email is already taken!");
        }

        // проверка наличия ролей
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName("ROLE_USER");
        if (userRole != null) {
            roles.add(userRole);
        }

        // устанавливаем пароль
        user.setPassword(password);

        // сохраняем пользователя в базу данных
        userService.registerUser(user, roles);

        return ResponseEntity.ok("User registered successfully!");
    }

}


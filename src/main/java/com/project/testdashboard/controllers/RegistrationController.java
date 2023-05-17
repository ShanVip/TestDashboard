package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.User;
import com.project.testdashboard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        // Проверяем, существует ли пользователь с указанным именем
        if (userRepository.existsByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Username or email is already exists");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        // Назначаем роль "USER" по умолчанию (ваше приложение может иметь другую систему ролей)
        user.setRole("USER");

        // Сохраняем пользователя в базе данных
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}


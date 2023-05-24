package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.User;
import com.project.testdashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-index";
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user-index";
    }

    @GetMapping("/{userId}/update")
    public String updateUserPage(@PathVariable long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "User not found!";
        }
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(
            @PathVariable("userId") long userId,
            @RequestParam("username") Optional<String> username,
            @RequestParam("email") Optional<String> email,
            @RequestParam("password") Optional<String> password
    ) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return "User not found!";
        }

        username.ifPresent(user::setUsername);
        email.ifPresent(user::setEmail);
        password.ifPresent(user::setPassword);

        userService.saveUser(user);

        return "redirect:/users/";
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}

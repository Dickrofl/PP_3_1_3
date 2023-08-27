package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;


@Controller
public class UserController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    WebSecurityConfig webSecurityConfig;

    @GetMapping("/")
    public String getHelloPage() {
        return "admin";
    }

    @GetMapping("/user")
    public String getUserPage(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String getUsersPage(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin";
    }
    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("newuser", new User());
        return "newUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("newuser") User user, @RequestParam("roles") Long roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            user.setRoles(Collections.singleton(role));
            String hashedPassword = webSecurityConfig.passwordEncoder().encode(user.getPassword()); // Хэшируем пароль
            user.setPassword(hashedPassword);
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUsers/{id}")
    public String editPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "editUsers";
    }

    @PostMapping("/editUsers")
    public String editUser(@ModelAttribute("user") User user) {
        String hashedPassword = webSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
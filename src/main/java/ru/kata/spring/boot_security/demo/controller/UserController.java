package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
public class UserController {
    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHelloPage() {
        return "admin";
    }

    @GetMapping("/user")
    public String getUserPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/admin")
    public String getUsersPage(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("roles", roleService.getListRoles());
        model.addAttribute("newuser", new User());
        return "newUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("newuser") User user, @RequestParam("roles") Long roleId) {
        userService.saveUser(user, roleId);
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
        model.addAttribute("roles", roleService.getListRoles());
        return "editUsers";
    }

    @PostMapping("/editUsers")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    private final UserService userService;
    @Autowired
    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role(1L,"ROLE_ADMIN");
        Role role2 = new Role(2L,"ROLE_USER");

        userService.addRole(role1);
        userService.addRole(role2);


        List<Role> roleAdmin = new ArrayList<>();
        List<Role> roleUser = new ArrayList<>();

        roleAdmin.add(role1);
        roleUser.add(role2);

        User user1 = new User(1L,"admin", "Админ", "Админов", "admin@mail.ru","admin", roleAdmin);
        User user2 = new User(2L,"user", "Юзер", "Юзеров",  "user@mail.ru","user",roleUser);
        User user3 = new User(3L, "user2", "ДаблЮзер", "ДаблЮзеров", "user2@mail.ru","user2",roleUser);

        userService.saveUser(user1,1L);
        userService.saveUser(user2,2L);
        userService.saveUser(user3,2L);
    }
}
package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface RoleService {
    Role getByIdRole(Long id);

    boolean add(Role user);

    List<Role> getListRoles();

    void setUserRoles(User user, Long roleId);

}

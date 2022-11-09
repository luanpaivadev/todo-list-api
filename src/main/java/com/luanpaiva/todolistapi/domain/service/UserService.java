package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Role;
import com.luanpaiva.todolistapi.domain.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    AppUser saveUser(final AppUser user);
    Role saveRole(final Role role);
    void addRoleToUser(final String username, final String roleName);
    AppUser getUser(final String username);
    List<AppUser> getUsers();

}

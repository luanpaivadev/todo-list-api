package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Authority;
import com.luanpaiva.todolistapi.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User saveUser(final User user);
    Authority saveAuthority(final Authority authority);
    void addAuthorityToUser(final String username, final String roleName);
    User getUser(final String username);
    List<User> getUsers();

}

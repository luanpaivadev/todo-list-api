package com.luanpaiva.todolistapi.domain.service;

import org.springframework.stereotype.Service;

import com.luanpaiva.todolistapi.domain.model.User;

@Service
public interface UserService {

    User save(final User user);

}

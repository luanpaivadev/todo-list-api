package com.luanpaiva.todolistapi.api.v1.controller;

import com.luanpaiva.todolistapi.domain.model.Authority;
import com.luanpaiva.todolistapi.domain.model.User;
import com.luanpaiva.todolistapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        return ok(userService.getUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody final User user) {
        return status(CREATED).body(userService.saveUser(user));
    }

    @PostMapping("/roles/save")
    public ResponseEntity<Authority> saveRole(@RequestBody final Authority authority) {
        return status(CREATED).body(userService.saveAuthority(authority));
    }
}

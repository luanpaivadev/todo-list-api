package com.luanpaiva.todolistapi.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.luanpaiva.todolistapi.domain.exceptions.RestTemplateException;
import com.luanpaiva.todolistapi.domain.service.LoginService;

@RestController
@RequestMapping("/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> login(final String username, final String password) {

        try {
            JsonNode accessToken = loginService.getAccessToken(username, password);
            return accessToken != null
                    ? ResponseEntity.ok(accessToken)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (RestTemplateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}

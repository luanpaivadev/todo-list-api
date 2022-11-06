package com.luanpaiva.todolistapi.api.v1.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.luanpaiva.todolistapi.domain.exceptions.RestTemplateException;
import com.luanpaiva.todolistapi.domain.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/keycloak/validate/token")
public class KeycloakController {

    @Autowired
    private KeycloakService keycloakService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> validateToken(@RequestParam("token") final String token) {

        try {
            JsonNode response = keycloakService.validateToken(token);
            final boolean active = response.get("active").asBoolean();
            return ResponseEntity.ok(active);
        } catch (RestTemplateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

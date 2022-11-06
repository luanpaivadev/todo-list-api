package com.luanpaiva.todolistapi.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.luanpaiva.todolistapi.domain.exceptions.RestTemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

    @Value("${username.authentication}")
    private String USERNAME_AUTHENTICATION;

    @Value("${password.authentication}")
    private String PASSWORD_AUTHENTICATION;

    @Value("${keycloak.auth-server-url}")
    private String KEYCLOAK_AUTH_SERVER_URL;

    @Autowired
    private RestTemplate restTemplate;

    public JsonNode validateToken(final String token) throws RestTemplateException {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> oauthPayload = new LinkedMultiValueMap<>();
            oauthPayload.add("client_id", USERNAME_AUTHENTICATION);
            oauthPayload.add("client_secret", PASSWORD_AUTHENTICATION);
            oauthPayload.add("token", token);

            HttpEntity<?> request = new HttpEntity<>(oauthPayload, headers);

            ResponseEntity<JsonNode> response = restTemplate
                    .postForEntity(KEYCLOAK_AUTH_SERVER_URL + "/realms/TodolistAuth/protocol/openid-connect/token/introspect", request,
                            JsonNode.class);

            return response.getStatusCode().equals(HttpStatus.OK) ? response.getBody() : null;
        } catch (Exception e) {
            if (e.getMessage().contains("401")) {
                return null;
            }
            throw new RestTemplateException(e.getMessage());
        }
    }
}

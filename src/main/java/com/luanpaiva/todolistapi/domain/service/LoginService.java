package com.luanpaiva.todolistapi.domain.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.luanpaiva.todolistapi.domain.exceptions.RestTemplateException;

@Service
public class LoginService {

//    @Value("${username.authentication}")
//    private String USERNAME_AUTHENTICATION;
//
//    @Value("${password.authentication}")
//    private String PASSWORD_AUTHENTICATION;
//
//    @Value("${keycloak.auth-server-url}")
//    private String KEYCLOAK_AUTH_SERVER_URL;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public JsonNode getAccessToken(final String username, final String password) throws RestTemplateException {
//
//        StringBuilder basicAuth = new StringBuilder();
//        basicAuth.append(USERNAME_AUTHENTICATION);
//        basicAuth.append(":");
//        basicAuth.append(PASSWORD_AUTHENTICATION);
//
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set("Authorization", "Basic ".concat(Base64.getEncoder().encodeToString(basicAuth.toString().getBytes())));
//
//            MultiValueMap<String, String> oauthPayload = new LinkedMultiValueMap<>();
//            oauthPayload.add("username", username);
//            oauthPayload.add("password", password);
//            oauthPayload.add("grant_type", "password");
//
//            HttpEntity<?> request = new HttpEntity<>(oauthPayload, headers);
//            ResponseEntity<JsonNode> response = restTemplate
//                    .postForEntity(KEYCLOAK_AUTH_SERVER_URL + "/realms/TodolistAuth/protocol/openid-connect/token", request,
//                            JsonNode.class);
//
//            return response.hasBody() ? response.getBody() : null;
//
//        } catch (Exception e) {
//            if (e.getMessage().contains("401")) {
//                return null;
//            }
//            throw new RestTemplateException(e.getMessage());
//        }
//
//    }

}

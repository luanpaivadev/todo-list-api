package com.luanpaiva.todolistapi;

import com.luanpaiva.todolistapi.domain.model.Role;
import com.luanpaiva.todolistapi.domain.model.AppUser;
import com.luanpaiva.todolistapi.domain.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class TodoListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {

        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveUser(new AppUser(null, "Luan Paiva", "luanpaivadev", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Alane Paiva", "alanepaiva", "1234", new ArrayList<>()));

            userService.addRoleToUser("luanpaivadev", "ROLE_ADMIN");
            userService.addRoleToUser("alanepaiva", "ROLE_USER");
        };
    }

}

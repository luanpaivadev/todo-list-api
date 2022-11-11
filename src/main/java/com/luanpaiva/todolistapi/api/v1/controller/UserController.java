package com.luanpaiva.todolistapi.api.v1.controller;

import static com.luanpaiva.todolistapi.utils.Roles.HAS_ROLE_ADMIN;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanpaiva.todolistapi.api.v1.assembler.UserAssembler;
import com.luanpaiva.todolistapi.api.v1.assembler.UserDisassembler;
import com.luanpaiva.todolistapi.api.v1.model.dto.UserDto;
import com.luanpaiva.todolistapi.api.v1.model.input.UserInput;
import com.luanpaiva.todolistapi.domain.repository.UserRepository;
import com.luanpaiva.todolistapi.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserAssembler userAssembler;
    private final UserDisassembler userDisassembler;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<List<UserDto>> findAll() {
        final var userList = userRepository.findAll();
        final var userDto = userDisassembler.toListDtoObject(userList);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody final UserInput userInput) {
        final var user = userAssembler.toDomainObject(userInput);
        final var userDto = userDisassembler.toDtoObject(userService.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<UserDto> update(@RequestBody final UserInput userInput, @PathVariable final Long userId) {

        try {
            final var userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                final var user = userOptional.get();
                modelMapper.map(userInput, user);
                final var userDto = userDisassembler.toDtoObject(userService.save(user));
                return ResponseEntity.status(HttpStatus.OK).body(userDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

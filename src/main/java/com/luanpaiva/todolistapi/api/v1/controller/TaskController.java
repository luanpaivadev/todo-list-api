package com.luanpaiva.todolistapi.api.v1.controller;

import static com.luanpaiva.todolistapi.utils.Roles.HAS_ANY_ROLE;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanpaiva.todolistapi.api.v1.assembler.TaskAssembler;
import com.luanpaiva.todolistapi.api.v1.assembler.TaskDisassembler;
import com.luanpaiva.todolistapi.api.v1.model.dto.TaskDto;
import com.luanpaiva.todolistapi.api.v1.model.input.TaskInput;
import com.luanpaiva.todolistapi.domain.exceptions.UserNotFoundException;
import com.luanpaiva.todolistapi.domain.repository.TaskRepository;
import com.luanpaiva.todolistapi.domain.repository.UserRepository;
import com.luanpaiva.todolistapi.domain.service.TaskServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasks")
public class TaskController {

    private final ModelMapper modelMapper;
    private final TaskServiceImpl taskService;
    private final TaskRepository taskRepository;
    private final TaskAssembler taskAssembler;
    private final TaskDisassembler taskDisassembler;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<List<TaskDto>> findAll() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var username = authentication.getName();
            final var taskList = taskRepository.findByUsername(username);
            final var taskDtoList = taskAssembler.toListDtoObject(taskList);
            return ResponseEntity.ok(taskDtoList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<TaskDto> save(@RequestBody final TaskInput taskInput) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final var username = authentication.getName();
            final var task = taskDisassembler.toDomainObject(taskInput);
            final var user = userRepository.findByUsername(username)
                    .orElseThrow(UserNotFoundException::new);
            task.setUser(user);
            final var taskDto = taskAssembler.toDtoObject(taskService.save(task));
            return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{taskId}")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<TaskDto> update(@RequestBody final TaskInput taskInput, @PathVariable final Long taskId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final var username = authentication.getName();
            final var taskOptional = taskRepository.findByIdAndUserUsername(taskId, username);
            if (taskOptional.isPresent()) {
                final var task = taskOptional.get();
                modelMapper.map(taskInput, task);
                final var taskDto = taskAssembler.toDtoObject(taskService.save(task));
                return ResponseEntity.status(HttpStatus.OK).body(taskDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<?> delete(@PathVariable final Long taskId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final var username = authentication.getName();
            final var taskOptional = taskRepository.findByIdAndUserUsername(taskId, username);
            if (taskOptional.isPresent()) {
                taskService.delete(taskOptional.get());
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

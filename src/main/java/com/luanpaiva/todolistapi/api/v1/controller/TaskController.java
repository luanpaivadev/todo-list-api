package com.luanpaiva.todolistapi.api.v1.controller;

import com.luanpaiva.todolistapi.api.v1.assembler.TaskAssembler;
import com.luanpaiva.todolistapi.api.v1.assembler.TaskDisassembler;
import com.luanpaiva.todolistapi.api.v1.model.dto.TaskDto;
import com.luanpaiva.todolistapi.api.v1.model.input.TaskInput;
import com.luanpaiva.todolistapi.domain.repository.TaskRepository;
import com.luanpaiva.todolistapi.domain.repository.UserRepository;
import com.luanpaiva.todolistapi.domain.service.TaskServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAssembler taskAssembler;

    @Autowired
    private TaskDisassembler taskDisassembler;

    @Autowired
    private UserRepository userRepository;

    private static final String HAS_ROLE_USER = "hasRole('ROLE_USER')";
    private static final String HAS_ROLE_ADMIN = "hasRole('ROLE_ADMIN')";

    @GetMapping
    @PreAuthorize(HAS_ROLE_USER)
    public ResponseEntity<List<TaskDto>> findAllOrderById() {
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
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<TaskDto> save(@RequestBody final TaskInput taskInput) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var task = taskDisassembler.toDomainObject(taskInput);
            final var username = authentication.getName();
            final var user = userRepository.findByUsername(username);
            task.setUser(user);
            final var taskDto = taskAssembler.toDtoObject(taskService.saveTask(task));
            return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{taskId}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<TaskDto> update(@RequestBody final TaskInput taskInput, @PathVariable final Long taskId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var username = authentication.getName();
            final var response = taskRepository.findByIdAndUserUsername(taskId, username);
            if (response.isPresent()) {
                var task = response.get();
                modelMapper.map(taskInput, task);
                final var taskDto = taskAssembler.toDtoObject(taskService.saveTask(task));
                return ResponseEntity.status(HttpStatus.OK).body(taskDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> delete(@PathVariable final Long taskId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var username = authentication.getName();
            final var response = taskRepository.findByIdAndUserUsername(taskId, username);
            return response.map(task -> {
                taskService.deleteTask(task);
                return ResponseEntity.noContent().build();
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

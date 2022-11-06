package com.luanpaiva.todolistapi.api.v1.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.luanpaiva.todolistapi.domain.repository.TaskRepository;
import com.luanpaiva.todolistapi.domain.service.TaskService;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAssembler taskAssembler;

    @Autowired
    private TaskDisassembler taskDisassembler;

    @RolesAllowed({ "user", "admin" })
    @GetMapping
    public ResponseEntity<List<TaskDto>> findAllOrderById() {
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var userId = authentication.getName();
            final var taskList = taskRepository.findAllOrderById(userId);
            final var taskDtoList = taskAssembler.toListDtoObject(taskList);
            return ResponseEntity.ok(taskDtoList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @RolesAllowed("admin")
    @PostMapping
    public ResponseEntity<TaskDto> save(@RequestBody final TaskInput taskInput) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var task = taskDisassembler.toDomainObject(taskInput);
            final var userId = authentication.getName();
            task.setUserId(userId);
            final var taskDto = taskAssembler.toDtoObject(taskService.saveTask(task));
            return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @RolesAllowed("admin")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> update(@RequestBody final TaskInput taskInput, @PathVariable final Long taskId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var userId = authentication.getName();
            final var response = taskRepository.findByUserIdAndTaskId(userId, taskId);
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

    @RolesAllowed("admin")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable final Long taskId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var userId = authentication.getName();
            final var response = taskRepository.findByUserIdAndTaskId(userId, taskId);
            return response.map(task -> {
                taskService.deleteTask(task);
                return ResponseEntity.noContent().build();
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

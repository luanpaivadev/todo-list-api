package com.luanpaiva.todolistapi.api.v1.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.luanpaiva.todolistapi.domain.model.Task;
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

    @GetMapping
    public ResponseEntity<List<Task>> findAllOrderById() {
        return ResponseEntity.ok(taskRepository.findAllOrderById());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> findById(@PathVariable final Long taskId) {

        final var response = taskRepository.findById(taskId);
        if (response.isPresent()) {
            final var taskDto = taskAssembler.toDtoObject(response.get());
            return ResponseEntity.status(HttpStatus.OK).body(taskDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody final TaskInput taskInput) {
        try {
            final var task = taskDisassembler.toDomainObject(taskInput);
            taskService.saveTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> update(@RequestBody final TaskInput taskInput, @PathVariable final Long taskId) {

        try {
            final var response = taskRepository.findById(taskId);
            if (response.isPresent()) {
                var task = response.get();
                modelMapper.map(taskInput, task);
                taskService.saveTask(task);
                return ResponseEntity.status(HttpStatus.OK).body(task);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable final Long taskId) {
        try {
            final var response = taskRepository.findById(taskId);
            return response.map(task -> {
                taskService.deleteTask(task);
                return ResponseEntity.noContent().build();
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

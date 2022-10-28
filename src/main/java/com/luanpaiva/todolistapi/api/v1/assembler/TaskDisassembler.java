package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luanpaiva.todolistapi.api.v1.model.input.TaskInput;
import com.luanpaiva.todolistapi.domain.model.Task;

@Component
public class TaskDisassembler implements MapperToDomainObject<TaskInput, Task> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Task toDomainObject(TaskInput taskInput) {
        return modelMapper.map(taskInput, Task.class);
    }

    @Override
    public List<Task> toListDomainObject(List<TaskInput> taskInputs) {
        return taskInputs.stream()
                .map(task -> modelMapper.map(task, Task.class))
                .collect(Collectors.toList());
    }

}

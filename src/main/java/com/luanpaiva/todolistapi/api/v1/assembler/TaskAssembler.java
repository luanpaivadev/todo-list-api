package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luanpaiva.todolistapi.api.v1.model.dto.TaskDto;
import com.luanpaiva.todolistapi.domain.model.Task;

@Component
public class TaskAssembler implements MapperToDtoObject<Task, TaskDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDto toDtoObject(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> toListDtoObject(List<Task> taskList) {
        return taskList.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

}

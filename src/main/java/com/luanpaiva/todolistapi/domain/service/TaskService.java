package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Task;
import com.luanpaiva.todolistapi.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task saveTask(final Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(final Task task) {
        taskRepository.delete(task);
    }
    
}

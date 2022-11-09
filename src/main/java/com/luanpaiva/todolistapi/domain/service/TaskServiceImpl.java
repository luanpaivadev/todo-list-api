package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Task;
import com.luanpaiva.todolistapi.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
    
}

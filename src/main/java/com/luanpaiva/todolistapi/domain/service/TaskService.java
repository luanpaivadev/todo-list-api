package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Task;

public interface TaskService {

    Task saveTask(final Task task);
    void deleteTask(final Task task);

}

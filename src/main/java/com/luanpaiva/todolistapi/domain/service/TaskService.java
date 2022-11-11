package com.luanpaiva.todolistapi.domain.service;

import com.luanpaiva.todolistapi.domain.model.Task;

public interface TaskService {

    Task save(final Task task);
    void delete(final Task task);

}

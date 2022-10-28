package com.luanpaiva.todolistapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luanpaiva.todolistapi.domain.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("Select t From Task t Order By t.completed asc, t.id asc")
    List<Task> findAllOrderById();
}

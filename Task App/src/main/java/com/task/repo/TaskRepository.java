package com.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}

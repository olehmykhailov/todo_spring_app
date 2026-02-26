package com.example.demo.tasks.datalayer.repositories;

import com.example.demo.tasks.datalayer.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

}

package com.example.demo.tasks.businesslayer.services;

import com.example.demo.infrastructure.exceptions.EntityNotFoundException;
import com.example.demo.tasks.businesslayer.dtos.*;
import com.example.demo.tasks.businesslayer.mappers.TaskMapper;
import com.example.demo.tasks.datalayer.entities.TaskEntity;
import com.example.demo.tasks.datalayer.repositories.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private TaskEntity findById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR:NOT_FOUND"));
    }

    public CreateTaskResponseDto createTask(CreateTaskRequestDto taskDto) {
        TaskEntity newTask = taskMapper.toCreateTaskEntity(taskDto);

        TaskEntity savedTask = taskRepository.save(newTask);

        return taskMapper.toCreateResponseDto(savedTask);
    }

    public List<GetTaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toGetResponseDto)
                .toList();
    }

    public GetTaskResponseDto getTaskById(UUID id) {
        return taskMapper.toGetResponseDto(findById(id));
    }

    public PatchTaskResponseDto updateTask(UUID id, PatchTaskRequestDto patchTaskDto) {
        TaskEntity taskEntity = findById(id);

        taskMapper.updateEntityFromDto(patchTaskDto, taskEntity);

        return taskMapper.toPatchResponseDto(taskRepository.save(taskEntity));
    }

    public void deleteTask(UUID id) {
        taskRepository.delete(findById(id));
    }
}

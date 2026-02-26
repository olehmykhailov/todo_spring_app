package com.example.demo.tasks.service;

import com.example.demo.infrastructure.exceptions.EntityNotFoundException;
import com.example.demo.tasks.businesslayer.dtos.*;
import com.example.demo.tasks.businesslayer.mappers.TaskMapper;
import com.example.demo.tasks.businesslayer.services.TasksService;
import com.example.demo.tasks.datalayer.entities.TaskEntity;
import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;
import com.example.demo.tasks.datalayer.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TasksService tasksService;

    @Test
    @DisplayName("createTask: Success - Should return CreateTaskResponseDto when task is successfully created")
    void createTask_ShouldReturnDto_WhenTaskIsCreated() {
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto("New Task", "Description");
        TaskEntity newTaskEntity = new TaskEntity();
        newTaskEntity.setTitle("New Task");
        newTaskEntity.setDescription("Description");
        newTaskEntity.setStatus(TaskStatusEnum.PENDING);

        TaskEntity savedTaskEntity = new TaskEntity();
        savedTaskEntity.setId(UUID.randomUUID());
        savedTaskEntity.setTitle("New Task");
        savedTaskEntity.setDescription("Description");
        savedTaskEntity.setStatus(TaskStatusEnum.PENDING);
        savedTaskEntity.setCreatedAt(LocalDateTime.now());

        CreateTaskResponseDto expectedResponse = new CreateTaskResponseDto(
                savedTaskEntity.getId(),
                "New Task",
                "Description",
                TaskStatusEnum.PENDING,
                savedTaskEntity.getCreatedAt()
        );

        when(taskMapper.toCreateTaskEntity(requestDto)).thenReturn(newTaskEntity);
        when(taskRepository.save(newTaskEntity)).thenReturn(savedTaskEntity);
        when(taskMapper.toCreateResponseDto(savedTaskEntity)).thenReturn(expectedResponse);

        CreateTaskResponseDto result = tasksService.createTask(requestDto);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(taskRepository, times(1)).save(newTaskEntity);
    }

    @Test
    @DisplayName("createTask: Error - Should throw RuntimeException when repository throws exception")
    void createTask_ShouldThrowException_WhenRepositoryFails() {
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto("New Task", "Description");
        TaskEntity newTaskEntity = new TaskEntity();

        when(taskMapper.toCreateTaskEntity(requestDto)).thenReturn(newTaskEntity);
        when(taskRepository.save(newTaskEntity)).thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tasksService.createTask(requestDto));
        assertEquals("DB Error", exception.getMessage());
        verify(taskRepository, times(1)).save(newTaskEntity);
    }

    @Test
    @DisplayName("getAllTasks: Success - Should return list of GetTaskResponseDto when tasks exist")
    void getAllTasks_ShouldReturnDtoList_WhenTasksExist() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(UUID.randomUUID());
        
        GetTaskResponseDto responseDto = new GetTaskResponseDto(
            taskEntity.getId(), "Title", "Desc", TaskStatusEnum.PENDING, LocalDateTime.now(), LocalDateTime.now()
        );

        when(taskRepository.findAll()).thenReturn(List.of(taskEntity));
        when(taskMapper.toGetResponseDto(taskEntity)).thenReturn(responseDto);

        List<GetTaskResponseDto> result = tasksService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDto, result.get(0));
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllTasks: Error - Should throw RuntimeException when repository throws exception")
    void getAllTasks_ShouldThrowException_WhenRepositoryFails() {
        when(taskRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tasksService.getAllTasks());
        assertEquals("DB Error", exception.getMessage());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getTaskById: Success - Should return GetTaskResponseDto when task exists")
    void getTaskById_ShouldReturnDto_WhenTaskExists() {
        UUID id = UUID.randomUUID();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);

        GetTaskResponseDto expectedResponse = new GetTaskResponseDto(
                id, "Title", "Desc", TaskStatusEnum.PENDING, LocalDateTime.now(), LocalDateTime.now()
        );

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toGetResponseDto(taskEntity)).thenReturn(expectedResponse);

        GetTaskResponseDto result = tasksService.getTaskById(id);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("getTaskById: Error - Should throw EntityNotFoundException when task does not exist")
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tasksService.getTaskById(id));
        assertEquals("ERR:NOT_FOUND", exception.getMessage());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("updateTask: Success - Should return PatchTaskResponseDto when task exists")
    void updateTask_ShouldReturnDto_WhenTaskExists() {
        UUID id = UUID.randomUUID();
        PatchTaskRequestDto patchDto = new PatchTaskRequestDto("Updated Title", null, null);
        
        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(id);
        existingTask.setTitle("Old Title");

        TaskEntity updatedTask = new TaskEntity();
        updatedTask.setId(id);
        updatedTask.setTitle("Updated Title");

        PatchTaskResponseDto responseDto = new PatchTaskResponseDto(
                id, "Updated Title", "Desc", TaskStatusEnum.PENDING
        );

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);
        when(taskMapper.toPatchResponseDto(updatedTask)).thenReturn(responseDto);

        PatchTaskResponseDto result = tasksService.updateTask(id, patchDto);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(taskRepository, times(1)).findById(id);
        verify(taskMapper, times(1)).updateEntityFromDto(patchDto, existingTask);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    @DisplayName("updateTask: Error - Should throw EntityNotFoundException when task does not exist")
    void updateTask_ShouldThrowException_WhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        PatchTaskRequestDto patchDto = new PatchTaskRequestDto("Title", null, null);
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tasksService.updateTask(id, patchDto));
        assertEquals("ERR:NOT_FOUND", exception.getMessage());
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteTask: Success - Should delete task when task exists")
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {
        UUID id = UUID.randomUUID();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));

        tasksService.deleteTask(id);

        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, times(1)).delete(taskEntity);
    }

    @Test
    @DisplayName("deleteTask: Error - Should throw EntityNotFoundException when task does not exist")
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tasksService.deleteTask(id));
        assertEquals("ERR:NOT_FOUND", exception.getMessage());
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, never()).delete(any());
    }
}

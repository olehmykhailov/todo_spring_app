package com.example.demo.tasks.businesslayer.controllers;

import com.example.demo.tasks.businesslayer.dtos.*;
import com.example.demo.tasks.businesslayer.services.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "tasks", description = "Orchestrating tasks")
public class TasksController {
    private final TasksService tasksService;

    @Operation(summary = "Get task by ID", description = "Returns task object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task exists and will be returned"),
            @ApiResponse(responseCode = "404", description = "Task doesn`t exist"),
    })
    @GetMapping("/{id}")
    public GetTaskResponseDto getTaskById(
            @PathVariable(name = "id") UUID id
    ) {
        return tasksService.getTaskById(id);
    }

    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping()
    public List<GetTaskResponseDto> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @Operation(summary = "Create new task", description = "Creates new task with NEW status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New task was successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping()
    public CreateTaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto) {
        return tasksService.createTask(createTaskRequestDto);
    }

    @Operation(summary = "Updates exist task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Task was successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Task doesn`t exist")
            }
    )
    @PatchMapping("/{id}")
    public PatchTaskResponseDto patchTask(
            @PathVariable(name = "id") UUID id,
            PatchTaskRequestDto patchTaskRequestDto
    ) {
        return tasksService.updateTask(id, patchTaskRequestDto);
    }

    @Operation(summary = "Deletes exist task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Task was successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Task doesn`t exist")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteTask(
            @PathVariable(name = "id") UUID id
    ) {
        tasksService.deleteTask(id);
    }

}

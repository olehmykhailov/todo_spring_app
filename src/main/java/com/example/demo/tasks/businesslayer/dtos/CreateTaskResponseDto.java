package com.example.demo.tasks.businesslayer.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;

public record CreateTaskResponseDto(
        UUID id,
        String title,
        String description,
        TaskStatusEnum status,
        LocalDateTime createdAt
) {
}

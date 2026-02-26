package com.example.demo.tasks.businesslayer.dtos;

import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetTaskResponseDto(
        UUID id,
        String title,
        String description,
        TaskStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

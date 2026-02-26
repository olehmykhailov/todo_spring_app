package com.example.demo.tasks.businesslayer.dtos;

import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;

import java.util.UUID;

public record PatchTaskResponseDto(
        UUID id,
        String title,
        String description,
        TaskStatusEnum status
) {
}

package com.example.demo.tasks.businesslayer.dtos;

import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;

public record PatchTaskRequestDto(
        String title,
        String description,
        TaskStatusEnum status
) {
}

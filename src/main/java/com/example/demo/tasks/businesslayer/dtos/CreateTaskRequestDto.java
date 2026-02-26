package com.example.demo.tasks.businesslayer.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequestDto(
        @Schema(description = "Task`s title", example = "Create API")
        @NotBlank
        String title,

        @Schema(description = "Task`s description", example = "Implement GET endpoints")
        String description
) {
}

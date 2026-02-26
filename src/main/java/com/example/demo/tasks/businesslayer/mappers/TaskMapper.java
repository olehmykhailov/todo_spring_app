package com.example.demo.tasks.businesslayer.mappers;

import com.example.demo.tasks.businesslayer.dtos.*;
import com.example.demo.tasks.datalayer.entities.TaskEntity;

import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;
import org.mapstruct.*;

@Mapper(componentModel = "spring", imports = {TaskStatusEnum.class})
public interface TaskMapper {

    GetTaskResponseDto toGetResponseDto(TaskEntity task);

    CreateTaskResponseDto toCreateResponseDto(TaskEntity task);

    @Mapping(target = "status", expression = "java(TaskStatusEnum.NEW)")
    TaskEntity toCreateTaskEntity(CreateTaskRequestDto createTaskRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PatchTaskRequestDto dto, @MappingTarget TaskEntity entity);

    PatchTaskResponseDto toPatchResponseDto(TaskEntity task);
}

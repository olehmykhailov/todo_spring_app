package com.example.demo.tasks.datalayer.entities;


import com.example.demo.infrastructure.BaseEntity;
import com.example.demo.tasks.datalayer.enums.TaskStatusEnum;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
@RequiredArgsConstructor
@Getter @Setter
public class TaskEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TaskStatusEnum status;
}

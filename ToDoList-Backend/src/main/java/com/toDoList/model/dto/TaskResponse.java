package com.toDoList.model.dto;

import com.toDoList.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaskResponse {

    private UUID id;

    private String title;

    private String description;

    private LocalDate deadline;

    private int priority;

    private String assignedMember;

    private String todoListTitle;

    public TaskResponse( Task task ,String todoListTitle ) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.priority = task.getPriority();
        this.assignedMember = task.getAssignedMember().getUser().getEmail();
        this.todoListTitle = todoListTitle;


    }
}

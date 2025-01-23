package com.toDoList.model.dto;

import com.toDoList.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaskResponse {

    private UUID id;

    private String title;

    private LocalDateTime deadline;

    private int priority;

    private String assignedMember;

    public TaskResponse( Task task ) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.deadline = task.getDeadline();
        this.priority = task.getPriority();
        this.assignedMember = task.getAssignedMember().getUser().getEmail();

    }
}

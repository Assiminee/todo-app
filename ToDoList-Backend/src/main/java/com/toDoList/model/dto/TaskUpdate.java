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

public class TaskUpdate {

    private String title;

    private String description;

    private LocalDate deadline;

    private int priority;

    private String status;

    public TaskUpdate( Task task, String status  ) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.priority = task.getPriority();
        this.status = status;




    }
}

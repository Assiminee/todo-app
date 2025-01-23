package com.toDoList.model.dto;

import com.toDoList.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String title;

    private String description;

    private LocalDate deadline; // Optional

    private int priority;

    private UUID assignedMemberId; // Optional
}
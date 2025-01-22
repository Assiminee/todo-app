package com.toDoList.model.dto;

import com.toDoList.model.TodoList;
import com.toDoList.model.User;
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

public class ListTodoListDTO {

    private UUID id;
    private String title;
    private String category;
    private LocalDate createdAt;
    private String owner;

    public ListTodoListDTO(TodoList todoList) {
        this.id = todoList.getId();
        this.title = todoList.getTitle();
        this.category = todoList.getCategory();
        this.createdAt = LocalDate.from(todoList.getCreatedAt());
        this.owner = todoList.getOwner().getEmail();
    }
}

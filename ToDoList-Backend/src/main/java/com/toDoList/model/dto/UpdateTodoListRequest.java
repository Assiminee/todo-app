package com.toDoList.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateTodoListRequest {

    private String title;        // Optional
    private String description;  // Optional
    private String category;     // Optional
    private List<AddMemberRequest> membersToAdd; // Optional

    public UpdateTodoListRequest(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }
}

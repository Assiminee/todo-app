package com.toDoList.controller;

import java.util.List;
import java.util.UUID;

import com.toDoList.model.dto.AddMemberRequest;
import com.toDoList.model.dto.ListTodoListDTO;
import com.toDoList.model.dto.TodoListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.toDoList.model.TodoList;
import com.toDoList.service.TodoListService;


@RestController
@RequestMapping("/api/todoLists")
public class TodoListController {
	
	private final TodoListService todoListService;

	@Autowired
	public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
	}
	
	
	@PostMapping
	public ResponseEntity<TodoList> createTodoList(
            @RequestHeader("Authorization") String jwt,
            @RequestBody TodoListDTO todoListDTO
            ) {

        try {
            TodoList todoList = todoListService.createTodoList(jwt, todoListDTO);
            return new ResponseEntity<>(todoList , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}


    @GetMapping
    public ResponseEntity<List<?>> getFilteredTodoLists(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false,defaultValue = "") String title,
            @RequestParam(required = false,defaultValue = "") String category,
            @RequestParam(required = false,defaultValue = "") String role
    ) {
        List<ListTodoListDTO> todoLists = todoListService.getFilteredTodoLists(jwt, title, category, role);
        return ResponseEntity.ok(todoLists);
    }

    @PostMapping("/{todoListId}/addMembers")
    public ResponseEntity<?> addMembersToTodoList(
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @RequestBody List<AddMemberRequest> membersToAdd
    ) {

        todoListService.addMembersToTodoList(jwt, todoListId, membersToAdd);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

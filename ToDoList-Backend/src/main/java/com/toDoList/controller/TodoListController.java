package com.toDoList.controller;

import java.util.List;

import com.toDoList.model.dto.TodoListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toDoList.model.TodoList;
import com.toDoList.service.TodoListService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


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
    public ResponseEntity<List<TodoList>> getFilteredTodoLists(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false,defaultValue = "") String title,
            @RequestParam(required = false,defaultValue = "") String category,
            @RequestParam(required = false,defaultValue = "") String role
    ) {
        List<TodoList> todoLists = todoListService.getFilteredTodoLists(jwt, title, category, role);
        return ResponseEntity.ok(todoLists);
    }
	
	
	
	
	
	

}

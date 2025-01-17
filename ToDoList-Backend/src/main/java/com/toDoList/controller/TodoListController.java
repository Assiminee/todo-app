package com.toDoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toDoList.model.TodoList;
import com.toDoList.service.TodoListService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/todoList")
public class TodoListController {
	
	private TodoListService todoListService;

	@Autowired
	public TodoListController(TodoListService todoListService) {
		this.todoListService = todoListService;
	}
	
	
	@PostMapping("/createTodoList")
	public ResponseEntity<TodoList> createTodoList(@RequestHeader("Authorization") String jwt,
			                                       @RequestParam("title") String title, 
			                                       @RequestParam("description") String description,
			                                       @RequestParam("category") String category) {
		
		
		TodoList todoList =  todoListService.createTodoList(jwt, title, description, category);
		
		return new ResponseEntity<>(todoList , HttpStatus.CREATED);
	}
	
	
	

}

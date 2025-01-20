package com.toDoList.controller;

import java.util.List;

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
@RequestMapping("/api/todoList")
public class TodoListController {
	
	private TodoListService todoListService;

	@Autowired
	public TodoListController(TodoListService todoListService) {
		this.todoListService = todoListService;
	}
	
	
	@PostMapping
	public ResponseEntity<TodoList> createTodoList(@RequestHeader("Authorization") String jwt,
			                                       @RequestParam("title") String title, 
			                                       @RequestParam("description") String description,
			                                       @RequestParam("category") String category) {
		
		
		TodoList todoList =  todoListService.createTodoList(jwt, title, description, category);
		
		return new ResponseEntity<>(todoList , HttpStatus.CREATED);
	}
	
	
	 //get all todolists that i own
    @GetMapping("/owned")
    public ResponseEntity<List<TodoList>> getOwnedTodoLists(@RequestHeader("Authorization") String jwt) {
    	
        List<TodoList> todoLists = todoListService.getTodoListsOwnedByUser(jwt);
        
        return new ResponseEntity<>(todoLists , HttpStatus.OK);
    }
    
    // Get all TodoLists where the user is a regular member
    @GetMapping("/member")
    public ResponseEntity<List<TodoList>> getTodoListsWhereUserIsMember(@RequestHeader("Authorization") String jwt) {
    	
        List<TodoList> todoLists = todoListService.getTodoListsWhereUserIsMember(jwt);
        
        return new ResponseEntity<>(todoLists , HttpStatus.OK);
    }

    // Get all TodoLists where the user is both an owner and a member
    @GetMapping("/owned-and-member")
    public ResponseEntity<List<TodoList>> getTodoListsOwnedAndMemberByUser(@RequestHeader("Authorization") String jwt) {
    	
        List<TodoList> todoLists = todoListService.getTodoListsOwnedAndMemberByUser(jwt);
        
        return new ResponseEntity<>(todoLists , HttpStatus.OK);
    }
	
	
	
	
	
	

}

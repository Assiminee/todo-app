package com.toDoList.service;

import org.springframework.stereotype.Service;

import com.toDoList.model.TodoList;

@Service
public interface TodoListService {
	
	public TodoList createTodoList(String jwt, String title, String description, String category);

}

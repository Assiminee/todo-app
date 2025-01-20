package com.toDoList.service;

import java.util.List;

import com.toDoList.model.dto.TodoListDTO;
import org.springframework.stereotype.Service;

import com.toDoList.model.TodoList;

@Service
public interface TodoListService {
	
	public TodoList createTodoList(String jwt, TodoListDTO todoListDTO) throws Exception;

    public List<TodoList> getFilteredTodoLists(String jwt, String title, String category, String role);

}

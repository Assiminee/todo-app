package com.toDoList.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toDoList.model.TodoList;

@Service
public interface TodoListService {
	
	public TodoList createTodoList(String jwt, String title, String description, String category);
	
    public List<TodoList> getTodoListsOwnedByUser(String jwt);
    
    public List<TodoList> getTodoListsWhereUserIsMember(String jwt);
    
    public List<TodoList> getTodoListsOwnedAndMemberByUser(String jwt);

}

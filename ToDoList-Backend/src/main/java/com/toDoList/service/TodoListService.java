package com.toDoList.service;

import java.util.List;
import java.util.UUID;

import com.toDoList.exceptions.ResourceNotFoundException;
import com.toDoList.exceptions.UnauthorizedException;
import com.toDoList.model.dto.ListTodoListDTO;
import com.toDoList.model.dto.TodoListDTO;
import com.toDoList.model.dto.UpdateTodoListRequest;
import org.springframework.stereotype.Service;

import com.toDoList.model.TodoList;

@Service
public interface TodoListService {
	
	public ListTodoListDTO createTodoList(String jwt, TodoListDTO todoListDTO) throws Exception;

    public List<ListTodoListDTO> getFilteredTodoLists(String jwt, String title, String category, String role);

    public TodoList getTodoListById(UUID id);

    public void addMembersToTodoListAndModify(String jwt, UUID todoListId, UpdateTodoListRequest updateRequest) throws IllegalArgumentException;

    public void deleteTodoList(String jwt, UUID todoListId)  throws UnauthorizedException, ResourceNotFoundException;

}

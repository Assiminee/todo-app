package com.toDoList.service;

import java.util.List;
import java.util.UUID;

import com.toDoList.model.dto.AddMemberRequest;
import com.toDoList.model.dto.ListTodoListDTO;
import com.toDoList.model.dto.TodoListDTO;
import org.springframework.stereotype.Service;

import com.toDoList.model.TodoList;

@Service
public interface TodoListService {
	
	public TodoList createTodoList(String jwt, TodoListDTO todoListDTO) throws Exception;

    public List<ListTodoListDTO> getFilteredTodoLists(String jwt, String title, String category, String role);

    public void addMembersToTodoList(String jwt, UUID todoListId, List<AddMemberRequest> membersToAdd);

}

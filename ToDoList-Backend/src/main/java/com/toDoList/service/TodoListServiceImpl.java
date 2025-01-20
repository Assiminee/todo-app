package com.toDoList.service;

import java.util.List;

import com.toDoList.model.*;
import com.toDoList.model.dto.TodoListDTO;
import com.toDoList.model.enums.Category;
import com.toDoList.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoList.repository.MemberRepository;
import com.toDoList.repository.TodoListRepository;

@Service
public class TodoListServiceImpl implements TodoListService {

	private TodoListRepository todoListRepository;

	private MemberRepository memberRepository;

	private UserService userService;

	@Autowired
	public TodoListServiceImpl(TodoListRepository todoListRepository, MemberRepository memberRepository,
			UserService userService) {
		super();
		this.todoListRepository = todoListRepository;
		this.memberRepository = memberRepository;
		this.userService = userService;
	}

	@Override
	public TodoList createTodoList(String jwt, TodoListDTO todoListDTO) throws Exception {
		
		User loggedInUser = userService.getProfile(jwt);

		String title = todoListDTO.getTitle();
		String category = todoListDTO.getCategory();
		String description = todoListDTO.getDescription();

		TodoList todoList = new TodoList(title, description, Category.valueOf(category).name(), loggedInUser);

		
		Member ownerMember = new Member(todoList, loggedInUser,Role.OWNER.name());

		// Save the TodoList (Cascade will save Members too)
		todoList.setMembers(List.of(ownerMember));
		return todoListRepository.save(todoList);
	}


	public List<TodoList> getFilteredTodoLists(String jwt, String title, String category, String role) {
		// Get logged-in user's profile
		User loggedInUser = userService.getProfile(jwt);

		// Pass filters to the repository
		return todoListRepository.filterTodoLists(
				loggedInUser.getId(),
				title == null ? "" : title,
				category == null ? "" : category,
				role == null ? "" : role
		);
	}
}

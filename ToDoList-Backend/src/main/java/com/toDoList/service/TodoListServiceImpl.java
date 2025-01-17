package com.toDoList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoList.model.Member;
import com.toDoList.model.TodoList;
import com.toDoList.model.User;
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
	public TodoList createTodoList(String jwt, String title, String description, String category) {
		
		User loggedInUser = userService.getProfile(jwt);

		
		TodoList todoList = new TodoList(title, description, category, loggedInUser);

		
		Member ownerMember = new Member(todoList, loggedInUser, "ALL", "MEMBER_ADMIN");

		// Save the TodoList (Cascade will save Members too)
		todoList.setMembers(List.of(ownerMember));
		return todoListRepository.save(todoList);
	}
}

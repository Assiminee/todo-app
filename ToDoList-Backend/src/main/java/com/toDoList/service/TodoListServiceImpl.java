package com.toDoList.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.toDoList.model.*;
import com.toDoList.model.dto.AddMemberRequest;
import com.toDoList.model.dto.ListTodoListDTO;
import com.toDoList.model.dto.TodoListDTO;
import com.toDoList.model.dto.UpdateTodoListRequest;
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
	public ListTodoListDTO createTodoList(String jwt, TodoListDTO todoListDTO) throws Exception {
		
		User loggedInUser = userService.getProfile(jwt);

		String title = todoListDTO.getTitle();
		String category = todoListDTO.getCategory();
		String description = todoListDTO.getDescription();

		TodoList todoList = new TodoList(title, description, Category.valueOf(category).name(), loggedInUser);

		
		Member ownerMember = new Member(todoList, loggedInUser,Role.OWNER.name());

		// Save the TodoList (Cascade will save Members too)
		todoList.setMembers(List.of(ownerMember));
		todoListRepository.save(todoList);

		return new ListTodoListDTO (todoList);
	}


	public List<ListTodoListDTO> getFilteredTodoLists(String jwt, String title, String category, String role) {

		User loggedInUser = userService.getProfile(jwt);

		// Pass filters to the repository
		List<TodoList> todoList = todoListRepository.filterTodoLists(
				loggedInUser.getId(),
				title == null ? "" : title,
				category == null ? "" : category,
				role == null ? "" : role
		);

		List<ListTodoListDTO> listTodoListDTOS = new ArrayList<>();

		for (TodoList list : todoList){

			listTodoListDTOS.add(new ListTodoListDTO(list));


		}

		return listTodoListDTOS;

	}

	@Override
	public TodoList getTodoListById(UUID id) {

		TodoList todoList = todoListRepository.findById(id).orElse(null);

        if (todoList != null) {
            todoList.getOwner().setPassword("NONE OF YOUR BUSINESS");
			for (Member member : todoList.getMembers()){
				member.getUser().setPassword("NONE OF YOUR BUSINESS");
			}
        }
        return todoList;
	}

	public void addMembersToTodoListAndModify(String jwt, UUID todoListId, UpdateTodoListRequest updateRequest) throws IllegalArgumentException {


		User loggedInUser = userService.getProfile(jwt);

		// Verify ownership
		if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
			throw new IllegalArgumentException("You are not authorized to add members to this TodoList.");
		}

		TodoList todoList = todoListRepository.findById(todoListId)
				.orElseThrow(() -> new IllegalArgumentException("TodoList not found"));

		if (updateRequest.getTitle() != null) {
			todoList.setTitle(updateRequest.getTitle());
		}

		if (updateRequest.getDescription() != null) {
			todoList.setDescription(updateRequest.getDescription());
		}

		if (updateRequest.getCategory() != null) {
			todoList.setCategory(Category.valueOf(updateRequest.getCategory()).name());
		}

		if (updateRequest.getMembersToAdd() != null && !updateRequest.getMembersToAdd().isEmpty()) {
			for (AddMemberRequest memberRequest : updateRequest.getMembersToAdd()) {
				// Fetch the user to be added
				User userToAdd = userService.getUserById(memberRequest.getUserId());


				// Check if user is already a member of the list
				if (memberRepository.existsByListAndUser(todoList, userToAdd)) {
					continue; // Skip if the user is already a member
				}

				// Create and save the new Member
				Member newMember = new Member();
				newMember.setUser(userToAdd);
				newMember.setList(todoList);
				newMember.setRole(Role.MEMBER.name());


				memberRepository.save(newMember);
			}
		}

		// Save changes to the TodoList
		todoListRepository.save(todoList);
	}

	@Override
	public void deleteTodoList(String jwt, UUID todoListId) throws Exception {

		User loggedInUser = userService.getProfile(jwt);

		// Verify ownership
		if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
			throw new Exception("You are not authorized delete this TodoList.");
		}

		TodoList todoList = todoListRepository.findById(todoListId)
				.orElseThrow(() -> new IllegalArgumentException("TodoList not found"));

		todoListRepository.deleteById(todoListId);
	}

}

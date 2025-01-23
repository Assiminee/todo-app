package com.toDoList.service;


import com.toDoList.model.Member;
import com.toDoList.model.Task;
import com.toDoList.model.TodoList;
import com.toDoList.model.User;
import com.toDoList.model.dto.TaskResponse;
import com.toDoList.model.enums.Status;
import com.toDoList.repository.MemberRepository;
import com.toDoList.repository.TaskRepository;
import com.toDoList.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TodoListRepository todoListRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskServiceImpl(TodoListRepository todoListRepository,
                           MemberRepository memberRepository,
                           TaskRepository taskRepository,
                           UserService userService) {
        this.todoListRepository = todoListRepository;
        this.memberRepository = memberRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public TaskResponse createTask(String jwt, UUID todoListId, String title, String description,
                                   LocalDateTime deadline, int priority, UUID assignedMemberId) throws Exception {

        User loggedInUser = userService.getProfile(jwt);

        // Verify ownership
        if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
            throw new Exception("You are not authorized create tasks in this TodoList.");
        }

        // Fetch the TodoList
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new IllegalArgumentException("TodoList not found"));

        Member assignedMember;

        if (assignedMemberId != null) {
            // Fetch the assigned member
            assignedMember = todoList.getMembers().stream()
                    .filter(member -> member.getId().equals(assignedMemberId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Assigned Member does not belong to this TodoList"));
        } else {
            // Default to the owner by finding the member with role "OWNER"
            assignedMember = todoList.getMembers().stream()
                    .filter(member -> member.getRole().equals("OWNER"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Owner not found in TodoList members"));
        }

        // Create and save the task
        Task task = new Task(title, description, deadline, Status.PENDING, priority, assignedMember);
        task.setTodoList(todoList);

        taskRepository.save(task);

        return new TaskResponse(task);
    }

    @Override
    public Task getTaskById(UUID taskId) throws Exception {
        return null;
    }
}

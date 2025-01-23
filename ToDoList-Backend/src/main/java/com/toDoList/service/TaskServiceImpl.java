package com.toDoList.service;


import com.toDoList.model.Member;
import com.toDoList.model.Task;
import com.toDoList.model.TodoList;
import com.toDoList.model.User;
import com.toDoList.model.dto.TaskUpdate;
import com.toDoList.model.enums.Category;
import com.toDoList.model.enums.Status;
import com.toDoList.repository.MemberRepository;
import com.toDoList.repository.TaskRepository;
import com.toDoList.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TodoListRepository todoListRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public TaskServiceImpl(TodoListRepository todoListRepository,
                           MemberRepository memberRepository,
                           TaskRepository taskRepository,
                           UserService userService,
                           EmailService emailService) {
        this.todoListRepository = todoListRepository;
        this.memberRepository = memberRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public Task createTask(String jwt, UUID todoListId, String title, String description,
                           LocalDate deadline, int priority, UUID assignedMemberId) throws Exception {

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
        assignedMember.getUser().setPassword("NONE OF YOUR BUSINESS");

        // Notify the assigned member via email
        String recipientEmail = assignedMember.getUser().getEmail();
        String subject = "New Task Assigned: " + title;
        String body = String.format(
                "<p>Hi %s,</p>" +
                        "<p>You have been assigned a new task:</p>" +
                        "<ul>" +
                        "<li><strong>Title:</strong> %s</li>" +
                        "<li><strong>Description:</strong> %s</li>" +
                        "<li><strong>Deadline:</strong> %s</li>" +
                        "</ul>" +
                        "<p>Best Regards,<br>TodoList Team</p>",
                assignedMember.getUser().getUserName(), title, description, deadline);

// Use true for HTML content
        emailService.sendEmail(recipientEmail, subject, body);

        return task;

    }

    @Override
    public Task getTaskById(UUID taskId) throws Exception {
        return null;
    }

    public List<Task> getTasksByDeadline(String jwt, LocalDate deadline) throws Exception {

        User loggedInUser = userService.getProfile(jwt);

        List<Task> tasks = taskRepository.findTasksByUserAndExactDeadline(loggedInUser.getId(), deadline);
        if (tasks.isEmpty())
            throw new Exception("you have no Tasks assigned to you.");

        for(Task task : tasks) {
            task.getAssignedMember().getUser().setPassword("NONE OF YOUR BUSINESS");
        }
        return tasks ;
    }

    @Override
    public void updateTaskAndChangeStatus(String jwt, UUID todoListId, UUID taskId, TaskUpdate updateTask) throws Exception {

        User loggedInUser = userService.getProfile(jwt);

        // Verify ownership
        if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
            throw new Exception("You are not authorized to modify tasks in this todolist.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (updateTask.getTitle() != null) {
            task.setTitle(updateTask.getTitle());
        }

        if (updateTask.getDescription() != null) {
            task.setDescription(updateTask.getDescription());
        }

        if (updateTask.getDeadline() != null) {
            task.setDeadline(updateTask.getDeadline());
        }
        if (updateTask.getStatus() != null) {
            task.setStatus(Status.valueOf(updateTask.getStatus()));
        }

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(String jwt, UUID todoListId,  UUID taskId) throws Exception {

        User loggedInUser = userService.getProfile(jwt);

        // Verify ownership
        if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
            throw new Exception("You are not authorized to delete tasks in this todolist.");
        }

        taskRepository.deleteById(taskId);

    }
}

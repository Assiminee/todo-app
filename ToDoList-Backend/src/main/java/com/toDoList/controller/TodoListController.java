package com.toDoList.controller;

import java.util.List;
import java.util.UUID;

import com.toDoList.model.Task;
import com.toDoList.model.User;
import com.toDoList.model.dto.*;
import com.toDoList.model.enums.Gender;
import com.toDoList.model.enums.Status;
import com.toDoList.repository.TaskRepository;
import com.toDoList.service.MemberService;
import com.toDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.toDoList.model.TodoList;
import com.toDoList.service.TodoListService;


@RestController
@RequestMapping("/api/todoLists")
public class TodoListController {
	
	private final TodoListService todoListService;

    private final MemberService memberService;

    private final TaskService taskService;

    private final TaskRepository taskRepository;

    @Autowired
    public TodoListController(TodoListService todoListService, MemberService memberService,
                              TaskService taskService, TaskRepository taskRepository) {
        this.todoListService = todoListService;
        this.memberService = memberService;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @PostMapping
	public ResponseEntity<?> createTodoList(
            @RequestHeader("Authorization") String jwt,
            @RequestBody TodoListDTO todoListDTO
            ) {

        try {
            ListTodoListDTO todoList = todoListService.createTodoList(jwt, todoListDTO);
            return new ResponseEntity<>(todoList , HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}


    @GetMapping
    public ResponseEntity<List<?>> getFilteredTodoLists(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false,defaultValue = "") String title,
            @RequestParam(required = false,defaultValue = "") String category,
            @RequestParam(required = false,defaultValue = "") String role
    ) {
        List<ListTodoListDTO> todoLists = todoListService.getFilteredTodoLists(jwt, title, category, role);
        return ResponseEntity.ok(todoLists);
    }

    @GetMapping("/{todoListId}")
    public ResponseEntity<TodoList> getTodoListById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId)
    {
        try {

            TodoList todoList = todoListService.getTodoListById(todoListId);
            if (todoList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{todoListId}")
    public ResponseEntity<?> addMembersToTodoListAndModify(
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @RequestBody UpdateTodoListRequest updateRequest
    ) {
        try {

            todoListService.addMembersToTodoListAndModify(jwt, todoListId, updateRequest);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Void> deleteTodoList (
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId)
    {
        try {

            todoListService.deleteTodoList(jwt, todoListId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{todoListId}/members/{memberId}")
    public ResponseEntity<Void> deleteMember (
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @PathVariable UUID memberId)
    {
        try {
            memberService.deleteMember(jwt, todoListId,memberId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/{todoListId}/tasks")
    public ResponseEntity<Task> createTask(
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @RequestBody TaskRequest request) {

        try {

            Task  task = taskService.createTask(
                    jwt,
                    todoListId,
                    request.getTitle(),
                    request.getDescription(),
                    request.getDeadline(),
                    request.getPriority(),
                    request.getAssignedMemberId() // Optional
            );
            return new ResponseEntity<>(task , HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{todoListId}/tasks/{taskId}")
    public ResponseEntity<?> completeTaskAndModify(
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId,
            @RequestBody TaskUpdate taskUpdate
    ) {
        try {

            taskService.updateTaskAndChangeStatus(jwt,todoListId,taskId,taskUpdate);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{todoListId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask (
            @RequestHeader("Authorization") String jwt,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId)
    {
        try {
            taskService.deleteTask(jwt,todoListId,taskId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}

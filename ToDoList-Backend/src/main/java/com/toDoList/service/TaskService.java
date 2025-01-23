package com.toDoList.service;


import com.toDoList.model.Task;
import com.toDoList.model.dto.TaskUpdate;
import com.toDoList.model.dto.UpdateTodoListRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface TaskService {
    public Task createTask(String jwt, UUID todoListId, String title, String description,
                           LocalDate deadline, int priority, UUID assignedMemberId) throws Exception;

    public Task getTaskById(UUID taskId) throws Exception;

    public List<Task> getTasksByDeadline(String jwt, LocalDate deadline) throws Exception;

    public void updateTaskAndChangeStatus(String jwt, UUID todoListId, UUID taskId, TaskUpdate updateTask) throws Exception;

    public void deleteTask(String jwt, UUID todoListId, UUID taskId) throws Exception;

}

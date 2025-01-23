package com.toDoList.service;


import com.toDoList.model.Task;
import com.toDoList.model.dto.TaskResponse;
import com.toDoList.model.enums.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public interface TaskService {
    public TaskResponse createTask(String jwt, UUID todoListId, String title, String description,
                                   LocalDateTime deadline, int priority, UUID assignedMemberId) throws Exception;

    public Task getTaskById(UUID taskId) throws Exception;

}

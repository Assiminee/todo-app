package com.toDoList.repository;


import com.toDoList.model.Task;
import com.toDoList.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

   boolean existsByTodoListAndTitle(TodoList todoList,String title);
}

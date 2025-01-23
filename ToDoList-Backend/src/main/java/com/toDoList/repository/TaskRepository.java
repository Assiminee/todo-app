package com.toDoList.repository;


import com.toDoList.model.Task;
import com.toDoList.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

   //boolean existsByTodoListAndTitle(TodoList todoList,String title);

   @Query("SELECT t FROM Task t " +
           "WHERE t.assignedMember.user.id = :userId " +
           "AND DATE(t.deadline) = DATE(:deadline)")
   List<Task> findTasksByUserAndExactDeadline(
           @Param("userId") UUID userId,
           @Param("deadline") LocalDate deadline
   );
}

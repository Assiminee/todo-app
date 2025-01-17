package com.toDoList.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoList.model.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, UUID> {

}

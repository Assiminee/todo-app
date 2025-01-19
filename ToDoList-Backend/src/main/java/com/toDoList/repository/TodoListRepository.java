package com.toDoList.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toDoList.model.TodoList;
import com.toDoList.model.User;


@Repository
public interface TodoListRepository extends JpaRepository<TodoList, UUID> {
	
	public List<TodoList> findAllByCategory (String category);
	
	public List<TodoList> findAllByOwner (User owner);
	
	 @Query("""
	           SELECT t FROM TodoList t
	           JOIN Member m ON t.id = m.list.id
	           WHERE t.owner.id = :userId AND m.user.id = :userId
	           """)
	    List<TodoList> findAllByOwnerAndMember(@Param("userId") UUID userId);
	
	

}

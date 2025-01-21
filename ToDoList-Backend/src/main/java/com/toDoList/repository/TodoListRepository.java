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

	@Query("""
       SELECT t FROM TodoList t
       JOIN Member m ON t.id = m.list.id
       WHERE m.user.id = :userId
       AND (:role = '' OR m.role = :role)
       AND (:category = '' OR t.category = :category)
       AND (:title = '' OR t.title LIKE CONCAT('%', :title, '%'))
       """)
	List<TodoList> filterTodoLists(
			@Param("userId") UUID userId,
			@Param("title") String title,
			@Param("category") String category,
			@Param("role") String role
	);

	@Query("""
       SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END
       FROM TodoList t
       WHERE t.id = :todoListId AND t.owner.id = :userId
       """)
	boolean isOwner(@Param("todoListId") UUID todoListId, @Param("userId") UUID userId);







}

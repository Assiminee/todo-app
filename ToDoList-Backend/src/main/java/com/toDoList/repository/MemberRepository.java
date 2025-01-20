package com.toDoList.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toDoList.model.Member;
import com.toDoList.model.TodoList;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	
	//   @Query("SELECT m.list FROM Member m WHERE m.user.id = :userId AND m.role = 'MEMBER_USER' ")
	  //  List<TodoList> findAllTodoListsByMemberId(@Param("userId") UUID userId);

}

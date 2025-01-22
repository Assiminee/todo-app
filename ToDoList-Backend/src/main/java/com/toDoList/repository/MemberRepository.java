package com.toDoList.repository;

import java.util.List;
import java.util.UUID;

import com.toDoList.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toDoList.model.Member;
import com.toDoList.model.TodoList;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    boolean existsByListAndUser(TodoList todoList, User user);

    Member findMemberById(UUID id);
}

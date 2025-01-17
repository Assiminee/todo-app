package com.toDoList.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoList.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

}

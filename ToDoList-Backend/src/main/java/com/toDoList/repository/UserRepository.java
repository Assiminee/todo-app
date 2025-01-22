package com.toDoList.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toDoList.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	User findByEmail(String username);

	boolean existsByEmail(String email);
	boolean existsByIdIsNotAndEmail(UUID id, String email);

	@Query("""
       SELECT u FROM User u
       WHERE (:keyword IS NULL OR u.userName LIKE CONCAT('%', :keyword, '%') OR u.email LIKE CONCAT('%', :keyword, '%'))
       """)
	List<User> searchUsers(@Param("keyword") String keyword);



}

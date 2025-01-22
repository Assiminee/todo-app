package com.toDoList.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Member {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private UUID id;

	    @CreationTimestamp
	    private LocalDateTime addedAt = LocalDateTime.now();
	    private String role;       // Use "OWNER" for owner or "MEMBER" for member

	    @ManyToOne
	    @JoinColumn(name = "list_id", nullable = false)
	    @JsonIgnore
	    private TodoList list;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	@OneToMany(mappedBy = "assignedMember", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> assignedTasks; // to be debated

	public Member(TodoList list, User user ,String role) {
	        this.list = list;
	        this.user = user;
	        this.role = role;
	    }

}

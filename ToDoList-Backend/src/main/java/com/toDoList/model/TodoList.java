package com.toDoList.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class TodoList {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String title;

	private String description;

	@CreationTimestamp
	private LocalDateTime createdAt ;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private String category;

	// The owner of the TodoList
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	// One TodoList has many members
	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Member> members;

	@OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks;

	public TodoList(String title, String description, String category, User owner) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.owner = owner;
	}

}

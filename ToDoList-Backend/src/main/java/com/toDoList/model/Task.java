package com.toDoList.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toDoList.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private int priority; // 0 (lowest) to 5 (highest)

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_member_id") // if a member is not assigned then it will default to the owner of the task
    private Member assignedMember;


    public Task(String title, String description, LocalDate deadline, Status status, int priority, Member assignedMember) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.priority = priority;
        this.assignedMember = assignedMember;
    }
}
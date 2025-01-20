package com.toDoList.model;

import java.util.Date;
import java.util.UUID;

import com.toDoList.model.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String profilePicture;
	
	private Gender gender;
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	

	public User(String userName, String email, String password, String profilePicture, Gender gender,
			Date birthDate) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.gender = gender;
		this.birthDate = birthDate;
	}
	
	

}

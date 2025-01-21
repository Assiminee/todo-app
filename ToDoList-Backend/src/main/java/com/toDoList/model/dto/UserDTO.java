package com.toDoList.model.dto;

import com.toDoList.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

	private UUID id;

	private String userName;

	private String email;

	private String profilePicture;

	private Gender gender;

	private Date birthDate;



	public UserDTO(String userName, String email, String profilePicture, Gender gender,
                   Date birthDate) {
		super();
		this.userName = userName;
		this.email = email;
		this.profilePicture = profilePicture;
		this.gender = gender;
		this.birthDate = birthDate;
	}
	
	

}

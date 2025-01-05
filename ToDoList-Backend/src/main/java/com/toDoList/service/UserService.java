package com.toDoList.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toDoList.model.User;


@Service
public interface UserService {
	
	public User getProfile (String jwt);

	public List<User> getAllUsers();
	

}

package com.toDoList.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toDoList.model.User;


@Service
public interface UserService {
	
	public User getProfile (String jwt);

	public List<User> getAllUsers();
	
	public void insertUser(User newUser, MultipartFile profilePicture) throws Exception;
	

}

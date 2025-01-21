package com.toDoList.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toDoList.model.User;


@Service
public interface UserService {
	
	public User getProfile (String jwt);

	public List<User> getAllUsers();

	public List<User> searchUsers(String keyword);
	
	public void insertUser(User newUser, MultipartFile profilePicture) throws Exception;
	
	public User updateUser(User existingUser, User updatedUser, MultipartFile profilePicture) throws Exception;
	
	public void deleteUser(User user) throws Exception;

	public User getUserById(UUID userId);
}

package com.toDoList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoList.config.JwtProvider;
import com.toDoList.model.User;
import com.toDoList.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	

	
	@Override
	public User getProfile(String jwt) {
		
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		return userRepository.findByEmail(email);
		
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}



}

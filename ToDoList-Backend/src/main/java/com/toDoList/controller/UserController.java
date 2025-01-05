package com.toDoList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toDoList.model.User;
import com.toDoList.service.UserService;





@RestController
@RequestMapping("/api/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/test")
	public ResponseEntity<String> test (@RequestHeader("Authorization") String jwt){

		User user = userService.getProfile(jwt);
		
		return new ResponseEntity<>("welcome to hell mfs", HttpStatus.OK);
	}
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile (@RequestHeader("Authorization") String jwt){
		
		
		User user = userService.getProfile(jwt);
	//	System.out.println(user);
		
		return new ResponseEntity<>(user , HttpStatus.OK);
	}



	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getUsers (@RequestHeader("Authorization") String jwt){

		List<User> users = userService.getAllUsers();

		return new ResponseEntity<>(users , HttpStatus.OK);
	}

		
	
	

	
}

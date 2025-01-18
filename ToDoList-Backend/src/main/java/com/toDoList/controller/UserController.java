package com.toDoList.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toDoList.model.Gender;
import com.toDoList.model.User;
import com.toDoList.repository.UserRepository;
import com.toDoList.service.UserService;





@RestController
@RequestMapping("/api/users")
public class UserController {

	
	
	private UserService userService;
	
    private PasswordEncoder passwordEncoder;
    
   
	private UserRepository userRepository;
    
    
	
    @Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder,UserRepository userRepository) {
		super();
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}


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



	@GetMapping
	public ResponseEntity<List<User>> getUsers (@RequestHeader("Authorization") String jwt){

		List<User> users = userService.getAllUsers();

		return new ResponseEntity<>(users , HttpStatus.OK);
	}
	
	
	
	@PutMapping
	public ResponseEntity<User> updateLoggedInUser (@RequestHeader("Authorization") String jwt, 
			  @RequestParam("userName") String fullName
			, @RequestParam("email") String email
			, @RequestParam("password") String password
			, @RequestParam("gender") String gender 
			, @RequestParam("birthDate") Date birthDate
			, @RequestParam("profilePicture") MultipartFile profilePicture) throws Exception{
	
		
		
        User isEmailExist = userRepository.findByEmail(email);
    	
    	if(isEmailExist != null) {
    		
    		throw new Exception("Email est déja utilisé par un autre compte");
    	}
    	
    	User loggedUser = userService.getProfile(jwt);
		
		User updatedUser = new User();
		
		if(gender.equals("Male")) {
			updatedUser.setGender(Gender.Male);
    	}else {
    		updatedUser.setGender(Gender.Female);
    	}
    	
		updatedUser.setBirthDate(birthDate);
		updatedUser.setEmail(email);
		updatedUser.setUserName(fullName);
		updatedUser.setPassword(passwordEncoder.encode(password));
		
		User user = userService.updateUser(loggedUser, updatedUser, profilePicture);
		
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	

		
	@DeleteMapping
	public ResponseEntity<String> deleteLoggedInUser (@RequestHeader("Authorization") String jwt) throws Exception{
	
		User user = userService.getProfile(jwt);
		
		userService.deleteUser(user);
		
		
		return new ResponseEntity<>("User deleted successfully",HttpStatus.NO_CONTENT);
	}
	
	

	
}

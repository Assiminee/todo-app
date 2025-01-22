package com.toDoList.controller;



import java.sql.Date;
import java.util.List;

import com.toDoList.model.dto.UserDTO;
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

import com.toDoList.model.enums.Gender;
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
	public ResponseEntity<?> getUserProfile (@RequestHeader("Authorization") String jwt){
		try {
			User user = userService.getProfile(jwt);

			UserDTO userDTO = new UserDTO();

			userDTO.setId(user.getId());
			userDTO.setUserName(user.getUserName());
			userDTO.setEmail(user.getEmail());
			userDTO.setProfilePicture(user.getProfilePicture());
			userDTO.setGender(user.getGender());
			userDTO.setBirthDate(user.getBirthDate());

			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	/*@GetMapping
	public ResponseEntity<List<User>> getUsers (@RequestHeader("Authorization") String jwt){

		List<User> users = userService.getAllUsers();

		return new ResponseEntity<>(users , HttpStatus.OK);
	}*/

	@GetMapping
	public ResponseEntity<List<User>> searchUsers(
			@RequestHeader("Authorization") String jwt,
			@RequestParam(required = false) String keyword) {

		List<User> users = userService.searchUsers(keyword);

		return new ResponseEntity<>(users , HttpStatus.OK);
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateLoggedInUser (@RequestHeader("Authorization") String jwt,
			  @RequestParam("userName") String fullName
			, @RequestParam("email") String email
			, @RequestParam("password") String password
			, @RequestParam("gender") String gender 
			, @RequestParam("birthDate") Date birthDate
			, @RequestParam("profilePicture") MultipartFile profilePicture) throws Exception
	{
	
		try {

			User loggedUser = userService.getProfile(jwt);

			boolean isEmailExist = userRepository.existsByIdIsNotAndEmail(loggedUser.getId(),email);

			if (isEmailExist) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}



			User updatedUser = new User();

			try {
				updatedUser.setGender(Gender.valueOf(gender));
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}


			updatedUser.setBirthDate(birthDate);
			updatedUser.setEmail(email);
			updatedUser.setUserName(fullName);
			updatedUser.setPassword(passwordEncoder.encode(password));

			User user = userService.updateUser(loggedUser, updatedUser, profilePicture);

			UserDTO userDTO = new UserDTO(user.getId(),fullName,email,profilePicture.getOriginalFilename()
					,Gender.valueOf(gender),birthDate);

			return new ResponseEntity<>(userDTO, HttpStatus.OK);

		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

		
	@DeleteMapping
	public ResponseEntity<Void> deleteLoggedInUser (@RequestHeader("Authorization") String jwt) throws Exception{

		try {

			User user = userService.getProfile(jwt);

			userService.deleteUser(user);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}
	
	

	
}

package com.toDoList.controller;




import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toDoList.Request.LoginRequest;
import com.toDoList.Response.AuthResponse;
import com.toDoList.config.JwtProvider;
import com.toDoList.model.Gender;
import com.toDoList.model.User;
import com.toDoList.repository.UserRepository;
import com.toDoList.service.CustomerUserServiceImplementation;
import com.toDoList.service.UserService;





@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private UserService userService;
	
	private CustomerUserServiceImplementation customUserDetails;
	
	@Autowired
	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
			CustomerUserServiceImplementation customUserDetails, UserService userService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetails = customUserDetails;
		this.userService = userService;
	}



	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(
			  @RequestParam("userName") String fullName
			, @RequestParam("email") String email
			, @RequestParam("password") String password
			, @RequestParam("gender") String gender 
			, @RequestParam("birthDate") Date birthDate
			, @RequestParam("picture") MultipartFile profilePicture) throws Exception{
		
		
		
    	User isEmailExist = userRepository.findByEmail(email);
    	
    	if(isEmailExist != null) {
    		
    		throw new Exception("Email est déja utilisé par un autre compte");
    	}
    	
    	//create new User
    	
    	User newUser = new User();
    	
    	if(gender.equals("Male")) {
    		newUser.setGender(Gender.Male);
    	}else {
    		newUser.setGender(Gender.Female);
    	}
    	
    	newUser.setBirthDate(birthDate);
    	newUser.setEmail(email);
    	newUser.setUserName(fullName);
    	newUser.setPassword(passwordEncoder.encode(password));
    	
    	
    	userService.insertUser(newUser, profilePicture);
    	
    	
    	
    	Authentication authentication = new UsernamePasswordAuthenticationToken(email , password);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	
    	String token = JwtProvider.generateToken(authentication);
    	
    	AuthResponse authResponse = new AuthResponse();
    	authResponse.setJwt(token);
    	authResponse.setMessage("Registered Successfully");
    	authResponse.setStatus(true);
    	
    	return new ResponseEntity<>(authResponse , HttpStatus.CREATED);
    	
	}
	
	
	
	
	 @PostMapping("/login")
	    public ResponseEntity<AuthResponse> signin (@RequestBody LoginRequest loginRequest){
	    	
			AuthResponse authResponse = new AuthResponse();
			
			try {
				String username = loginRequest.getEmail();
				String password = loginRequest.getPassword();
				
                System.out.println("Login Successful");
				System.out.println(username+ " ------- " +password);
				

				Authentication authentication = authenticate(username , password);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String token = JwtProvider.generateToken(authentication);

				authResponse.setMessage("Login Successful");
				authResponse.setJwt(token);
				authResponse.setStatus(true);
				
				return new ResponseEntity<>(authResponse , HttpStatus.OK);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				authResponse.setMessage(e.getMessage());
				authResponse.setJwt(null);
				authResponse.setStatus(false);
				
				return new ResponseEntity<>(authResponse , HttpStatus.UNAUTHORIZED);
			}
	    	
	    	
	    }
	    
	 
	 	//authenticate methode to check user and motdepasse
	    private Authentication authenticate(String username, String password) {
	    	
	    	UserDetails userDetails = customUserDetails.loadUserByUsername(username);
	    	
	    	System.out.println("Sign in userDetails - " +userDetails);
	    	
	    	if(userDetails == null) {
	    		System.out.println("Sign in UserDetails - null " + userDetails);
	    		throw new BadCredentialsException("Invalid username or password");
	    	}
	    	
	    	if(!passwordEncoder.matches(password, userDetails.getPassword())) {
	    		System.out.println("sign in userDetails - password not match " +userDetails);
	    		throw new BadCredentialsException("Invalid username or password");
	    	}
	    	
	    	return new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
	    	
	    	
	    }
	
	
	

}

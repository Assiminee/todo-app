package com.toDoList.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toDoList.config.JwtProvider;
import com.toDoList.model.User;
import com.toDoList.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private final String uploadDir = System.getProperty("user.dir") + "/uploads";

	
	
	@Override
	public User getProfile(String jwt) {

		String email = JwtProvider.getEmailFromJwtToken(jwt);

		return userRepository.findByEmail(email);

	}

	
	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	
	@Override
	public void insertUser(User newUser, MultipartFile profilePicture) throws Exception {

		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String originalFileName = profilePicture.getOriginalFilename();

		if (originalFileName != null && !originalFileName.isEmpty()) {
			String fileExtension = "";
			int dotIndex = originalFileName.lastIndexOf(".");
			if (dotIndex != -1) {
				fileExtension = originalFileName.substring(dotIndex);
			}
			String uniqueFileName = originalFileName.replace(fileExtension, "") + "_" + Instant.now().toEpochMilli()
					+ fileExtension;
			Path filePath = uploadPath.resolve(uniqueFileName);
			profilePicture.transferTo(filePath.toFile());
			
			newUser.setProfilePicture(uniqueFileName);
			
			userRepository.save(newUser);

		}
	}


	@Override
	public void deleteUser(User user) throws Exception {
		
		userRepository.delete(user);
		
	}


	@Override
	public User updateUser(User existingUser, User updatedUser, MultipartFile profilePicture) throws Exception {
	
		
		if(updatedUser.getUserName() != null ) {
			existingUser.setUserName(updatedUser.getUserName());
		}
		if(updatedUser.getEmail() != null ) {
			existingUser.setEmail(updatedUser.getEmail());
		}
		if(updatedUser.getBirthDate() != null ) {
			existingUser.setBirthDate(updatedUser.getBirthDate());
		}
		if(updatedUser.getPassword() != null ) {
			existingUser.setPassword(updatedUser.getPassword());
		}
		if(updatedUser.getGender() != null ) {
			existingUser.setGender(updatedUser.getGender());
		}
		
		
		
		if(profilePicture !=null) {
			
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalFileName = profilePicture.getOriginalFilename();

			if (originalFileName != null && !originalFileName.isEmpty()) {
				String fileExtension = "";
				int dotIndex = originalFileName.lastIndexOf(".");
				if (dotIndex != -1) {
					fileExtension = originalFileName.substring(dotIndex);
				}
				String uniqueFileName = originalFileName.replace(fileExtension, "") + "_" + Instant.now().toEpochMilli()
						+ fileExtension;
				Path filePath = uploadPath.resolve(uniqueFileName);
				profilePicture.transferTo(filePath.toFile());
				
				existingUser.setProfilePicture(uniqueFileName);
				
			}
		}
		
		
		return userRepository.save(existingUser);
	}
	
	

}

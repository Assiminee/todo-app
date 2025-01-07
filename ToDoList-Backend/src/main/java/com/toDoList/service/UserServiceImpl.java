package com.toDoList.service;

import java.io.IOException;
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

}

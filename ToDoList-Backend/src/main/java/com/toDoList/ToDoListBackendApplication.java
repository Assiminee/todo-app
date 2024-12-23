package com.toDoList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoListBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListBackendApplication.class, args);
		System.out.println("OK");
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}

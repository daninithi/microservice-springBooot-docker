package com.spring_boot.user_service.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.user_service.modal.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
//	@GetMapping
//	public String getUsers() {
//		return "hello Api";
//	}
	
	@GetMapping
	public List<User> getUsers() {
		return Arrays.asList(new User(1L,"Jon","john@gmail.com"),new User(2L,"Joe","joe@gmail.com"));
	}
}

package com.spring_boot.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.user_service.entity.User;
import com.spring_boot.user_service.exception.ResourceNotFoundException;
import com.spring_boot.user_service.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
//	@GetMapping
//	public String getUsers() {
//		return "hello Api";
//	}
	
	@GetMapping
	public List<User> getUsers() {
//		return Arrays.asList(new User(1L,"Jon","john@gmail.com"),new User(2L,"Joe","joe@gmail.com"));
		return userRepository.findAll();
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) {
//		System.out.println("user Data:"+user.getName()+","+user.getEmail());
		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public User getUserbyId(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found "+ id));
	}

	@PutMapping("/{id}")
	public User updateUserbyId(@PathVariable Long id, @RequestBody User user) {
		 User userData =  userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found "+ id));
		 userData.setEmail(user.getEmail());
		 userData.setName(user.getName());
		 return userRepository.save(userData);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		User userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found "+ id));
		userRepository.delete(userData);
		return ResponseEntity.ok().build();
	}
}

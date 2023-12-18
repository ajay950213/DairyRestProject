package com.rest_api.dairy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest_api.dairy.entity.User;
import com.rest_api.dairy.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users/")
	public List<User> getUsers() {
		
		List<User> users = userService.findAll();
		
		return users;
		
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") long id) {
		
		User user = userService.findUserById(id);
		
		return user;
	}
	
	@PostMapping("/saveusers/")
	public List<User> saveUsers(@RequestBody List<User> users) {
	    List<User> responseUsers = userService.saveUsers(users);
	    return responseUsers;
	}
	
	
	
	@PostMapping("/users/")
	public User saveUser(@RequestBody User user) {
		
		User responseUser = userService.saveUser(user);
		
		return responseUser;
		
	}
	
	
	@PutMapping("/users/")
	public User updateUser(@RequestBody User user) {
		User updatedUser = userService.updateUser(user);
		
		return updatedUser;
	}
	
	
	@PutMapping("/users/{id}")
	public User updateUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		
		User user1 = userService.findUserById(id);
		
		user1.setId(id);
		user1.setPassword(user.getPassword());
		user1.setUsername(user.getUsername());
		
		User updatedUser = userService.updateUser(user1);
		
		return updatedUser;
		
	}
	
	@PatchMapping("/users/{id}")
	public User patchUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		
		User existingUser = userService.findUserById(id);
		
		long userId = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		
		if(username!=null && username.length()>0)
			existingUser.setUsername(username);
		if(password!=null && password.length()>0)
			existingUser.setPassword(password);
		if(userId>0)
			existingUser.setId(userId);
		
		
		User updatedUser = userService.updateUser(existingUser);
	
		return updatedUser;
		
	}
	
	
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable long id) {
		
		User user = userService.findUserById(id);
		
		String output = userService.deleteUser(user);
		
		return output;
	}
	
	
}

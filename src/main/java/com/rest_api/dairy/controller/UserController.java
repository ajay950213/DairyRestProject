package com.rest_api.dairy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest_api.dairy.entity.User;
import com.rest_api.dairy.service.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public ResponseEntity<?> getUsers() {
		
		try {
			List<User> users = userService.findAll();
			
			if (users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		
			return ResponseEntity.ok().body(users);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		try {
			if(id<=0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user id");
			}
				
				Optional<User> user = userService.findById(id);
				
				if(user == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
				}
				return ResponseEntity.ok(user);
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveUsers(@RequestBody List<User> users) {
		
		try {
			List<User> responseUsers = userService.saveUsers(users);
			
			if(responseUsers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUsers);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(responseUsers);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	

	@PostMapping("/users/")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		
		try {
			User responseUser = userService.saveUser(user);
			
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUser);
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	@PutMapping("/users/")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		try {
			User updatedUser = userService.updateUser(user);
		
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User "+updatedUser.getId()+" not fond");
			}
			return ResponseEntity.ok(updatedUser);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		try {
			
			if(id<=0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user id");
			}
			
				User user1 = userService.findUserById(id);
				
				if(user1 == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with"+id+" not found");
				}
				
				user1.setId(id);
				user1.setPassword(user.getPassword());
				user1.setUsername(user.getUsername());
				
				User updatedUser = userService.updateUser(user1);
				
				return ResponseEntity.ok(updatedUser);
			
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	
	}
	
	
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> patchUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		
		try {
				if(id<=0) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user id");
				}
				
				User existingUser = userService.findUserById(id);
				
				if(existingUser == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
				}
				
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
			
				return ResponseEntity.status(HttpStatus.OK).body("User updated "+updatedUser);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
			
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		try {
				if(id<=0) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user id");
				}
			
				Optional<User> user = userService.findById(id);
				if(user == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resource found");
				}
				
				String output = userService.deleteUser(user);
				
				return ResponseEntity.status(HttpStatus.OK).body(output);
	
			
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}


	@GetMapping("/allUserIds")
	public ResponseEntity<?> getAlUserIds(){
		try {
			List<Long> allUserIds = userService.findAllUserIds();
			return ResponseEntity.status(HttpStatus.OK).body(allUserIds);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}

}

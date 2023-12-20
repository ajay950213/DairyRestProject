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
	
	List<Long> userIdList = userService.findAllUserIds();

	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers() {
		
		try {
			List<User> users = userService.findAll();
			
			if (users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		
			return ResponseEntity.ok().body(users);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		try {
			if(id<=0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			if(userIdList.contains(id)) {
				User user = userService.findUserById(id);
				
				if(user == null) {
					return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
				}
				return ResponseEntity.ok(user);
			}
			else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PostMapping("/")
	public ResponseEntity<List<User>> saveUsers(@RequestBody List<User> users) {
		
		try {
			List<User> responseUsers = userService.saveUsers(users);
			
			if(responseUsers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUsers);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(responseUsers);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	

	@PostMapping("/users/")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		
		try {
			User responseUser = userService.saveUser(user);
			
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUser);
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	
	@PutMapping("/users/")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		try {
			User updatedUser = userService.updateUser(user);
			
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedUser);
			}
			return ResponseEntity.ok(updatedUser);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		try {
			if(userIdList.contains(id)) {
				User user1 = userService.findUserById(id);
				
				if(user1 == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
				
				user1.setId(id);
				user1.setPassword(user.getPassword());
				user1.setUsername(user.getUsername());
				
				User updatedUser = userService.updateUser(user1);
				
				return ResponseEntity.ok(updatedUser);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<User> patchUserbyId(@PathVariable("id") long id, @RequestBody User user) {
		
		try {
			if(userIdList.contains(id)) {
				
				User existingUser = userService.findUserById(id);
				
				if(existingUser == null) {
					return ResponseEntity.notFound().build();
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
			
				return ResponseEntity.ok(updatedUser);
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
		try {
			if(userIdList.contains(id)) {
				
				User user = userService.findUserById(id);
				if(user == null) {
					return ResponseEntity.notFound().build();
				}
				
				String output = userService.deleteUser(user);
				return ResponseEntity.status(HttpStatus.OK).body(output);
				
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@GetMapping("/allUserIds")
	public ResponseEntity<List<Long>> getAlUserIds(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userIdList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}

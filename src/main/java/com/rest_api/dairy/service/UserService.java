package com.rest_api.dairy.service;

import java.util.List;
import java.util.Optional;

import com.rest_api.dairy.entity.User;

public interface UserService {

	public User saveUser(User user);
	public List<User> saveUsers(List<User> users);
	public User findUserById(long id);
	
	public User updateUser(User user);
	public String deleteUser(User user);
	public User findById(long id);
	public List<User> findAll();
	public List<Long> findAllUserIds();
//	public User getUserByUsername(String username);
	
	
}

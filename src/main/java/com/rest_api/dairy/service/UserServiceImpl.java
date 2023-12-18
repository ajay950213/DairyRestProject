package com.rest_api.dairy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest_api.dairy.entity.User;
import com.rest_api.dairy.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findUserById(long id) {
		
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public String deleteUser(User user) {
		
		userRepository.delete(user);
		
		return "User deleted it is "+user;
	}

	@Override
	public User findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	
	@Override
	public List<User> saveUsers(List<User> users) {
		
		return userRepository.saveAll(users);
		
	}

	

	
}

package com.ibm.abcemailsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.abcemailsystem.entity.User;
import com.ibm.abcemailsystem.exceptions.UserNotFoundException;
import com.ibm.abcemailsystem.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public User saveUser(User user) {
		return repository.save(user);
	}


	public User getUserByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public List<User> findAllUsers( ) {
		return repository.findAll();
	}
	
	public User getUserById(Long id) {
		return repository.findById(id).get();
	}
	
	public boolean deleteUser(Long id) {
		Optional<User> user= repository.findById(id);
		if (user.isPresent()) {
			repository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateUser(Long id, User updatedUserDetails) throws UserNotFoundException {
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			User updatedUser = getUserById(id);
			updatedUser.setId(id);
			updatedUser.setFirstName(updatedUserDetails.getFirstName());
			updatedUser.setLastName(updatedUserDetails.getLastName());
			updatedUser.setEmail(updatedUserDetails.getEmail());
			updatedUser.setPassword(updatedUserDetails.getPassword());
			repository.save(updatedUser);
		}
		else {
			throw new UserNotFoundException("User not found.");
		}
	}
	
	
	
}

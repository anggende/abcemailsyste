package com.ibm.abcemailsystem.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.abcemailsystem.bean.UserBean;
import com.ibm.abcemailsystem.entity.User;
import com.ibm.abcemailsystem.exceptions.UserNotFoundException;
import com.ibm.abcemailsystem.service.UserService;

import javassist.bytecode.stackmap.TypeData.ClassName;

@RestController
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="addUser", consumes="application/json", produces="application/json")
	public ResponseEntity<String> addUser(@RequestBody UserBean userBean){
		User user = userService.saveUser(userBean.convertToUser());
		if (user!=null) {
			return ResponseEntity.ok("User successfully added.");
		}
		else {
			return ResponseEntity.badRequest().body("User registration failed.");
		}
	}
	
	@GetMapping(value="/user/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(userService.findAllUsers());
	}

	
	@GetMapping(value="/user")
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
		if (userService.getUserByEmail(email)!=null) {
			return ResponseEntity.ok().body(userService.getUserByEmail(email));
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@GetMapping(value="/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		if(userService.getUserById(id)!=null) {
			return ResponseEntity.ok().body(userService.getUserById(id));
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@DeleteMapping(value="/user/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("Account deleted.");
		} catch (UserNotFoundException e) {
			LOGGER.log(Level.INFO, "Account deletion failed due to: " + e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} finally {
			LOGGER.log(Level.INFO, "called deleteUser");
		}
	}
	
	@PutMapping(value="/user/update/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user){
		try {	
			userService.updateUser(id, user);
			return ResponseEntity.ok("Account updated.");
		} catch (UserNotFoundException e) {
			LOGGER.log(Level.INFO, "User update failed due to: " + e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} finally {
			LOGGER.log(Level.INFO, "called updateUser");
		}
	}
	
	@PostMapping(value="/login",consumes="application/json",produces="application/json")
	@ResponseBody
	public String loginUser(@RequestBody String email, String password) {
		User user = userService.getUserByEmail(email);
		if(user!=null) {
			return "Found.";
		}
		return "Not found";
	}
}

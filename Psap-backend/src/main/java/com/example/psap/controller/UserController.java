package com.example.psap.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.psap.dao.UserRepository;
import com.example.psap.entity.Login;
import com.example.psap.entity.Users;
import com.example.psap.service.IUserService;



@RestController
@RequestMapping(value = "/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/register")
	public ResponseEntity<Users> saveUser(@Valid @RequestBody Users user) {
		return new ResponseEntity<Users>(userService.addUser(user), HttpStatus.CREATED);
	}

	@PostMapping(value="/login")
	public ResponseEntity<Users> login(@Valid @RequestBody Login login) {
		
		Boolean isLoginSuccess=userService.login(login.getUsername(),login.getPassword());
		String msg="";
		Users ur=null;
		if(isLoginSuccess)
		{
			ur=userRepository.findByusername(login.getUsername());
			msg="welcome "+login.getUsername();
			return new ResponseEntity<Users>(ur,HttpStatus.OK);
		}
		else
		{
			msg="Invalid Credential,Try Again";
			return new ResponseEntity<Users>(ur,HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping(value = "/update/{user_id}")
	public ResponseEntity<Users> updateUser(@PathVariable Integer user_id, @RequestBody Users user) {
		Users updatedUser = userService.updateUser(user_id, user);
		return new ResponseEntity<Users>(updatedUser, HttpStatus.CREATED);
	}

	
	@GetMapping(value = "/showAllUsers")
	public ResponseEntity<List<Users>> showAllUsers() {
		List<Users> allUsers = userService.showAllUsers();
		return new ResponseEntity<List<Users>>(allUsers, HttpStatus.OK);
	}

	@GetMapping(value = "/showUser/{user_id}")
	public ResponseEntity<Users> getUser(@PathVariable Integer user_id) {
		Users userById = userService.showUser(user_id);
		return new ResponseEntity<Users>(userById, HttpStatus.OK);
	}

}
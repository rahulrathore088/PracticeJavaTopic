package com.lp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lp.model.Actor;
import com.lp.services.UserServices;

@RestController
@RequestMapping("/lp")
public class LearningPlatformController {

	@Autowired
	private UserServices userServices;
	
	@GetMapping("/home")
	public String getHome() {
		return "Welcome to landing page.";
	}
	
	@GetMapping("/user/{userName}")
	public Actor getUser(@PathVariable("userName") String userName) {
		return userServices.getUserName(userName);
	}
	
	@PostMapping("/saveUser")
	public Long saveUser(@Valid @RequestBody Actor actorDto) { 
		Long response = userServices.saveUser(actorDto);
		System.out.println(response);
		return response;
	}
	
	@DeleteMapping("/deleteUserByUserName/{userName}")
	public void deleteUser(@PathVariable("userName") String userName) {
		userServices.deleteUser(userName);
	}
}

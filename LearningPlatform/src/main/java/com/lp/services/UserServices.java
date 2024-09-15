package com.lp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lp.model.Actor;
import com.lp.repository.UserRepository;

@Component
public class UserServices {

	@Autowired
	private UserRepository userRepository;

	public Actor getUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public Long saveUser(Actor actor) {
		return userRepository.save(actor).getActorId();
	}
	
	public void deleteUser(String userName) {
		userRepository.deleteByUserName(userName);
	}

}

package com.swr.learn.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swr.learn.entites.User;
import com.swr.learn.repository.UserDetailsRepo;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDetailsRepo.findByUserName(username);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("User not found with username " + username);
		}
		return user;
	}

}

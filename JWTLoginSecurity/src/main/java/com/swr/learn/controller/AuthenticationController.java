package com.swr.learn.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swr.learn.config.JWTTokenHelper;
import com.swr.learn.entites.User;
import com.swr.learn.requests.AuthenticationRequest;
import com.swr.learn.response.AuthenticationResponse;
import com.swr.learn.response.UserInfo;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService detailsService;

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		String jwtToken = jwtTokenHelper.generateToken(user.getUsername());

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setToken(jwtToken);
		return ResponseEntity.ok(authenticationResponse);
	}
	
	@GetMapping("/auth/userinfo")
	public ResponseEntity<?>  getUserInfo(Principal user) {
		User userObj = (User) detailsService.loadUserByUsername(user.getName());
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(userObj.getFirstName());
		userInfo.setLastName(userObj.getLastName());
		//userInfo.setUserName(userObj.getUsername());
		userInfo.setRoles(userObj.getAuthorities().toArray());
		return ResponseEntity.ok(userInfo);
	}
}

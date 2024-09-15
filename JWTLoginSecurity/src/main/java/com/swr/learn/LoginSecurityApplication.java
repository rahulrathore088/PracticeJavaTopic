package com.swr.learn;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.swr.learn.entites.Authority;
import com.swr.learn.entites.User;
import com.swr.learn.repository.UserDetailsRepo;

@SpringBootApplication
public class LoginSecurityApplication {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	public static void main(String[] args) {
		SpringApplication.run(LoginSecurityApplication.class, args);
	}

	//@PostConstruct
	protected void init() {
		List<Authority> autorities = new ArrayList<>();
		autorities.add(createAuthority("USER", "User role"));
		autorities.add(createAuthority("ADMIN", "Admin role"));
		User user = new User();
		user.setUserName("raman");
		user.setFirstName("raman");
		user.setLastName("singh");
		user.setPassword(encoder.encode("raman"));
		user.setEnabled(true);
		user.setAuthorities(autorities);
		userDetailsRepo.save(user);
	}

	private Authority createAuthority(String roleCode, String roleDesc) {
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDescriptin(roleDesc);
		return authority;
	}

}

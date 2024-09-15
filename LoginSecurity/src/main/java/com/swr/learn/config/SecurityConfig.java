package com.swr.learn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.swr.learn.services.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserService userService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private RestAuthEntryPoint restAuthEntryPoint;
	
	
	/*
	 * @Bean public SecurityFilterChain filterChan(HttpSecurity http) throws
	 * Exception { http.authorizeHttpRequests().anyRequest().authenticated();
	 * http.csrf().disable().headers().frameOptions().disable(); http.formLogin();
	 * http.httpBasic(); return http.build(); }
	 */

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService() { UserDetails
	 * user = User.withDefaultPasswordEncoder().username("user")
	 * .password("password").roles("USER") .build(); return new
	 * InMemoryUserDetailsManager(user); }
	 */
	
	@Bean
	public SecurityFilterChain filterChan(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint(restAuthEntryPoint)
		.and()
		.authorizeHttpRequests((request) -> request.antMatchers("/api/v1/auth/login").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
		.addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper), 
				UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().headers().frameOptions().disable();
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	 

}

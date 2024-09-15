package com.swr.learn.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	private UserDetailsService detailsService;
	private JWTTokenHelper jwtTokenHelper;

public JWTAuthenticationFilter(UserDetailsService detailsService, JWTTokenHelper jwtTokenHelper) {
	this.detailsService = detailsService;
	this.jwtTokenHelper = jwtTokenHelper;
}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = jwtTokenHelper.getToken(request);
		if(authToken!=null) {
			String userName = jwtTokenHelper.getUserNameFromToken(authToken);
			if(userName!=null) {
				UserDetails userDetails = detailsService.loadUserByUsername(userName);
				if(jwtTokenHelper.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
							(userDetails, null, userDetails.getAuthorities());
					token.setDetails(new  WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}

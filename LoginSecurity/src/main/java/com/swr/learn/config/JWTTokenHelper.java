package com.swr.learn.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenHelper {

	@Value("${jwt.auth.app}")
	private String appName;

	@Value("${jwt.auth.secret_key}")
	private String secretKey;

	@Value("${jwt.auth.expires_in}")
	private int expiresIn;

	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String getUserNameFromToken(String token) {
		String userName;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			userName = claims.getSubject();
		} catch (Exception e) {
			userName = null;
		}
		return userName;
	}

	public String generateToken(String userName) {
		return Jwts.builder().setIssuer(appName).setIssuedAt(new Date()).setExpiration(generateExpDt())
				.signWith(signatureAlgorithm, secretKey).setSubject(userName).compact();
	}

	private Date generateExpDt() {
		return new Date(new Date().getTime() + expiresIn * 1000);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return (
				userName != null && userName.equals(userDetails.getUsername()) && !isTokenExpired(token)
				);
	}

	private boolean isTokenExpired(String token) {
		Date expireDt = getExpireDt(token);
		return expireDt.before(new Date());
	}

	private Date getExpireDt(String token) {
		Date expireDt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expireDt = claims.getExpiration();
		} catch (Exception e) {
			expireDt = null;
		}
		return expireDt;
	}
	
	public Date getIssueAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}
	
	public String getAuthHeaderFromToken(HttpServletRequest servletRequest) {
		return servletRequest.getHeader("Authorization");
	}
	
	public String getToken(HttpServletRequest servletRequest) {
		String authHeader = getAuthHeaderFromToken(servletRequest);
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

}

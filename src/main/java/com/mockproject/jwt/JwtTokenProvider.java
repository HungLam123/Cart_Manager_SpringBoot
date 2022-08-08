package com.mockproject.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	/*
	 	after have user information, we encryption of user information into JWT by JwtTokenProvider 
	 */
	
	//This JWT_SECRET part is secret, only the sever knows
	private final String JWT_SECRET = "Springboot-t4-2022";
	
	//thoi gian hieu luc cua jwt tinh bang miliseconds
	private static final long JWT_EXPIRATION = 604800000L;
	
	public String generateToken(CustomerUserDetails customerUser) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		//create string token by username of customerUserDetails
		return Jwts.builder()
					.setSubject(customerUser.getUsers().getUsername())
					.setIssuedAt(now)
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
					.compact();
	}
	
	//take user infomation by string token
	public String getUsernameFormJWT(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(JWT_SECRET)
							.parseClaimsJws(token)
							.getBody();
		return claims.getSubject();
	}
	
	public Boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");// không hợp lệ
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");//hết hạn
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");//không hỗ trợ
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");//chuỗi JWT trống
        }
		return false;
	}
}

package com.mockproject.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.Users;
import com.mockproject.jwt.CustomerUserDetails;
import com.mockproject.jwt.JwtTokenProvider;
import com.mockproject.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Override
	public Users doLogin(String username, String password, HttpSession session) throws Exception {
		UsernamePasswordAuthenticationToken authenInfo = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenManager.authenticate(authenInfo);
		
		CustomerUserDetails customerUser = (CustomerUserDetails) authentication.getPrincipal();
		Users userResponse = customerUser.getUsers();
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		session.setAttribute(SessionConstant.JWT, tokenProvider.generateToken(customerUser));
		return userResponse;
	}

}

package com.mockproject.Oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.Users;
import com.mockproject.service.UsersService;

@Component
public class Oauth2LoginSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private UsersService usersService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		
		String clientName = oauth2User.getClientName();
		String username = oauth2User.getEmail();
		String fullname = oauth2User.getFullname();
		String password = Long.toHexString(System.currentTimeMillis()); //Random password for the system
		String email = oauth2User.getEmail();
		
		Users emailUser = usersService.findByEmail(email);
		
		if(emailUser == null) {
			//register
			Users usersReponse = usersService.proccessOauthPostLogin(username, fullname, password, email, clientName);
			session.setAttribute(SessionConstant.CURRENT_USER, usersReponse);
			response.sendRedirect("/index");
		}else {
			//update existing user
			Users usersReponse = usersService.updateUserAfterOauthLoginSuccess(email);
			session.setAttribute(SessionConstant.CURRENT_USER, usersReponse);
			response.sendRedirect("/index");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

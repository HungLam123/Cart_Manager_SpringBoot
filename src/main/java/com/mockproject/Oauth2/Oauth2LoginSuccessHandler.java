package com.mockproject.Oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mockproject.entity.AuthenticationProvider;
import com.mockproject.entity.Users;
import com.mockproject.service.UsersService;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private UsersService usersService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
		
		String username = oauth2User.getEmail();
		String fullname = oauth2User.getFullname();
		String password = Long.toHexString(System.currentTimeMillis()); //mật khẩu ngẫu nhiên từ hệ thống
		String email = oauth2User.getEmail();
		
		Users emailUser = usersService.findByEmail(email);
		Users usernameUser = usersService.findByUsername(username);
		
		if(emailUser == null && usernameUser == null) {
			//register
			usersService.proccessOauthPostLogin(username, fullname, password, email, AuthenticationProvider.GOOGLE);				
		}else {
			//update existing user
			usersService.updateUserAfterOauthLoginSuccess(usernameUser, fullname, AuthenticationProvider.GOOGLE);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

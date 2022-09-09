package com.mockproject.Oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomOauth2User implements OAuth2User{

	private String clientName;
	
	private OAuth2User oauth2User;
	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return oauth2User.getAttribute("name");
	}

	public String getEmail() {
		return oauth2User.<String>getAttribute("email");
	}
	
	public String getFullname() {
		return oauth2User.getAttribute("name");
	}
	
	public String getClientName() {
		return this.clientName;
	}
}

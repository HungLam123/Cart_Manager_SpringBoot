package com.mockproject.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mockproject.entity.Users;

import lombok.Data;

@Data
public class CustomerUserDetails implements UserDetails {
	
	/*
		Default spring security is use Userdetails containing all user infomation
		because we are create a class transfer user infomation to userdetails.
	 */
	
	private static final long serialVersionUID = 1L;

	private Users users;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomerUserDetails(Users users, Collection<? extends GrantedAuthority> authorities) {
		this.users = users;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return users.getHashPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return users.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return users.getEnabled();
	}

}

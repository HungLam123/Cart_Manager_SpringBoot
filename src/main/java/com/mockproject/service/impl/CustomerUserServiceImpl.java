package com.mockproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mockproject.entity.Users;
import com.mockproject.jwt.CustomerUserDetails;
import com.mockproject.service.UsersService;

@Service
public class CustomerUserServiceImpl implements UserDetailsService {

	@Autowired
	private UsersService usersService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//check user in the database
		Users user = usersService.findByUsername(username);
		if(user == null || user.getIsDeleted() || !user.getEnabled()) {
			System.out.println("User not found: " + username);
			throw new UsernameNotFoundException(username);
		}
		try {
			List<GrantedAuthority> grantList = new ArrayList<>();
			GrantedAuthority authotity = new SimpleGrantedAuthority(user.getRoles().getDescription());
			grantList.add(authotity);
			return new CustomerUserDetails(user, grantList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.Users;

public interface UsersService {
	Users findByUsername(String username);
	
	Users findByEmail(String email);
	
	Users findByIdUser(Long id);
	
	Users findByUsernameIsdeletedEnabled(String username);
	
	Users doLogin(String username, String password);
	
	Users save(Users user) throws Exception;
	
	List<Users> findAll();
	
	void deleteLogical(String username);
	
	void update(Users user);
	
	//forgotpass
	void updateResetPassword(String token, String email)throws Exception;
	
	Users getByResetPasswordToken(String token);
	
	void updatePassword(Users user, String password);
	
	//changePassword
	void changePassword(Users user, String newPassword);

	Users register(Users user) throws Exception;
	void sendVerifycationEmail(Users user, String siteURL) throws Exception;
	
	public Boolean verify(String verifyCationCode);
	
	void updateUserDetails(Users user);

	Users updateUserAfterOauthLoginSuccess(String emailUser);

	Users proccessOauthPostLogin(String username, String fullname, String password, String email, String clientName);
}

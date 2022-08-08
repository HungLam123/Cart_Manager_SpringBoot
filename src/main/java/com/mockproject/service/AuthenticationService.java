package com.mockproject.service;

import javax.servlet.http.HttpSession;

import com.mockproject.entity.Users;

public interface AuthenticationService {

	Users doLogin(String username, String password, HttpSession session) throws Exception;
}

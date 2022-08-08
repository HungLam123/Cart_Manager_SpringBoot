package com.mockproject.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCryptpw {
	public static void main(String[] args) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String password = "111";
		String hashpassword = "$2a$10$asQgdvDPVg7J8AsjaAeb2uBsezgKKrFAO.YYQjmKuJU9WfQWFBmjC";
		System.err.println(bcrypt.matches(password, hashpassword));
//		System.out.println(bcrypt.encode(password));
		//endcode mã hoá
		//matches giải mã
	}
}
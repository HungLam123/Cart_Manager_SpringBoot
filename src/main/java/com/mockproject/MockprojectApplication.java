package com.mockproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class MockprojectApplication {

	public static void main(String[] args) {
//		Security.setProperty("jdk.tls.disabledAlgorithms",
//				"SSLv3, RC4, DES, MD5withRSA, DH keySize < 1024,EC keySize < 224, 3DES_EDE_CBC, anon, NULL");
		SpringApplication.run(MockprojectApplication.class, args);
	}

}

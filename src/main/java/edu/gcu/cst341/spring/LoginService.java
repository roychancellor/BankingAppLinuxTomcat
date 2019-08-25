package edu.gcu.cst341.spring;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	protected boolean isValidCredentials(String username, String password) {
		if(username.equals("admin") && password.equals("pass") ||
			username.equals("faculty") && password.equals("pass")) {
			return true;
		}
		return false;
	}
}

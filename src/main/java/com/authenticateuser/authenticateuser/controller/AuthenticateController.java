package com.authenticateuser.authenticateuser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authenticateuser.authenticateuser.model.User;

@RestController("/")
public class AuthenticateController {

	@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
	@PostMapping("credentials")
	public ResponseEntity<String> validateUser(@RequestBody User user) {
		String message = validatePassword(user.getUsername(),user.getPassword());
		
		
		return message == null? new ResponseEntity<String>("User details are valid",HttpStatus.CREATED):new ResponseEntity<String>(message,HttpStatus.UNPROCESSABLE_ENTITY);

	}

	public static String validatePassword(String username, String password) {
		String message = null;
		// Rule 1: Password should not contain the username in any case
		if (password.toLowerCase().contains(username.toLowerCase())) {
			message = "Password should not contain the username in any case";
		}

		// Rule 2: Password should be longer than 3 characters
		if (password.length() <= 3) {
			message = "Password should be longer than 3 characters";
		}

		// Rule 3: Password should not contain the same 3 characters directly in a row
		for (int i = 0; i < password.length() - 5; i++) {
			if (password.charAt(i) == password.charAt(i + 3) && password.charAt(i + 1) == password.charAt(i + 4)
					&& password.charAt(i + 2) == password.charAt(i + 5)) {
				message = "Password should not contain the same 3 characters directly in a row";
			}
		}

		return message;
	}
	
}

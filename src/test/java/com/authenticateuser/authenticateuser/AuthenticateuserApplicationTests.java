package com.authenticateuser.authenticateuser;

import com.authenticateuser.authenticateuser.controller.AuthenticateController;
import com.authenticateuser.authenticateuser.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthenticateuserApplicationTests {

    @Autowired
    private AuthenticateController controller;

    @Test
    public void testValidateUser_ValidCredentials_ReturnsCreatedResponse() {
        // Arrange
        User user = new User("username", "password");

        // Act
        ResponseEntity<String> response = controller.validateUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User details are valid", response.getBody());
    }

    @Test
    public void testValidateUser_PasswordContainsUsername_ReturnsUnprocessableEntityResponse() {
        // Arrange
        User user = new User("username", "username123");

        // Act
        ResponseEntity<String> response = controller.validateUser(user);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Password should not contain the username in any case", response.getBody());
    }

    @Test
    public void testValidateUser_ShortPassword_ReturnsUnprocessableEntityResponse() {
        // Arrange
        User user = new User("username", "pas");

        // Act
        ResponseEntity<String> response = controller.validateUser(user);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Password should be longer than 3 characters", response.getBody());
    }

    @Test
    public void testValidateUser_RepeatingCharacters_ReturnsUnprocessableEntityResponse() {
        // Arrange
        User user = new User("username", "abcabcaa");

        // Act
        ResponseEntity<String> response = controller.validateUser(user);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Password should not contain the same 3 characters directly in a row", response.getBody());
    }
}

package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.RockDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Rock;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PasswordControllerTest {

  private PasswordController PasswordController;
  private UserDAO mockUserDAO;

  @BeforeEach
  public void setupUserController() {
      mockUserDAO = mock(UserDAO.class);
      PasswordController = new PasswordController(mockUserDAO);
  }


  @Test
  public void testGenerateStrongPassword() throws IOException {
        // Invoke
        ResponseEntity<String> response = PasswordController.generateStrongUserPassword();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
  }

  @Test
  public void testGenerateStrongPasswordHandleException() throws Exception {
    
    doThrow(new IOException()).when(mockUserDAO).generateStrongUserPassword();

    ResponseEntity<String> response = PasswordController.generateStrongUserPassword();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
  }
  
}

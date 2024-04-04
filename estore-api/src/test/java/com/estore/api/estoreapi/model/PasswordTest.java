package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Rock class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class PasswordTest {

    @SuppressWarnings("static-access")
    @Test
    public void testValidPassword() {
      // Setup
      Password password_tester = new Password();
      String valid_password = "He1!oWorld";
      String invalid_password = "IamWeak";
      boolean valid_password_result = false;
      boolean invalid_password_result = false;

      // Invoke
      valid_password_result = password_tester.validPassword(valid_password);
      invalid_password_result = password_tester.validPassword(invalid_password);
      
      // Analyze
      assertTrue(valid_password_result);
      assertFalse(invalid_password_result);
    }


    @Test
    public void testCreateStrongPassword() {
      // Setup
      String password;
      boolean actual_result = false;

      // Invoke
      password = Password.createStrongPassword();
      actual_result = Password.validPassword(password);
      
      // Analyze
      assertTrue(actual_result);
    }

    @Test
    public void testHash() {
        // Setup
        String password = "He1!oWorld";

        String expected_hash = "78c0e72b7fa101674d6fd3c184d1e7863b87dd320c585026cb34c8b3433efa5a";
        String hashed;

        // Invoke
        try{
          hashed = Password.hashPassword(password);
        } catch (NoSuchAlgorithmException e){
          hashed = "";
        }
        
        // Analyze
        assertEquals(expected_hash, hashed);
    }
}
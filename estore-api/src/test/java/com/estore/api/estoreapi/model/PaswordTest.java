package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Rock class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class PaswordTest {

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
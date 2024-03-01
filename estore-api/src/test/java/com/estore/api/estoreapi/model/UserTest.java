package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the User class
 * 
 * @author Ryan Lembo-Ehms
 */
@Tag("Model-tier")
public class UserTest {
    
    /**
     * @author Ryan Lembo-Ehms
     */
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 21;
        String expected_username = "RJLE";
        String expected_password = "Password";

        // Invoke
        User user = new User(expected_id, expected_username, expected_password);

        // Analyze
        assertEquals(expected_id, user.getId());
    }

    /**
     * @author Ryan Lembo-Ehms
     */
    @Test
    public void testSetUsername() {
        // Setup
        int id = 21;
        String username = "RJLE";
        String password = "Password";

        // Invoke
        User user = new User(id, username, password);
        
        String expected_username = "Lembooooo";

        // Invoke
        user.setName(expected_username);

        // Analyze
        assertEquals(expected_username, user.getName());
    }

    /**
     * @author Ryan Lembo-Ehms
     */
    @Test
    public void testSetPassword() {
        // Setup
        int id = 21;
        String username = "RJLE";
        String password = "Password";

        // Invoke
        User user = new User(id, username, password);

        String expected_password = "NeWpAsSwOrD";

        // Invoke
        user.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password, user.getPassword());
    }

    /**
     * @author Ethan Battaglia
     */
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String username = "EJB";
                String password = "thisIsMyPassword!!1";

        String expected_string = String.format(User.STRING_FORMAT, id, username, password);

        User user = new User(id, username, password);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}

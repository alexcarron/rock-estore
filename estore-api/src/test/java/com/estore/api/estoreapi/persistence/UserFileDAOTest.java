package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Rock;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the User File DAO class
 *
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(10, "admin", "admin");
        testUsers[1] = new User(11, "Galactic Agent",  "igneous");
        testUsers[2] = new User(12, "Ice Gladiator",  "sedimentary");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the user array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    /**
     * @author Alex Carron
     */
    @Test
    public void testGetUsers() {
        // Invoke
        User[] users = userFileDAO.getUsers();

        // Analyze
        assertEquals(users.length, testUsers.length);

				int testUsersIndex = 0;
				for (User user : users) {
					assertEquals(user, testUsers[testUsersIndex]);
					testUsersIndex++;
				}
    }

    /**
     * @author Alex Carron
     */
    @Test
    public void testFindUsers() {
        // Invoke
        User[] usersContainingLa = userFileDAO.findUsers("la");

        // Analyze
        assertEquals(usersContainingLa.length, 2);
        assertEquals(usersContainingLa[0], testUsers[1]);
        assertEquals(usersContainingLa[1], testUsers[2]);
    }

    /**
     * @author Alex Carron
     */
    @Test
    public void testGetUser() {
        // Invoke
        User user = userFileDAO.getUser(10);

        // Analzye
        assertEquals(user, testUsers[0]);
    }

    @Test
    public void testDeleteUser() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(10),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test rocks array - 1 (because of the delete)
        // Because rocks attribute of RockFileDAO is package private
        // we can access it directly
        assertEquals(userFileDAO.users.size(),testUsers.length-1);
    }


    /**
     * @author Victor Rabinovich
     */
    @Test
    public void testCreateUser() {
        // Setup
        User user = new User(13, "Wonder-Person",  "johnson");

        User sameName = new User(14,"Galactic Agent", "iceicebaby");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user),
                                "Unexpected exception thrown");

        User resultSameName = assertDoesNotThrow(() -> userFileDAO.createUser(sameName),
                                "Can't have same username as someone else");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getId());
        assertEquals(actual.getId(),user.getId());
        assertEquals(actual.getName(),user.getName());

        assertNull(resultSameName);
    }


    /**
     * @author Alex Carron
     */
    @Test
    public void testCreateAdminUser() {
        // Setup
        User admin_user = new User(1, "admin",  "password");

        // Invoke
        User result = assertDoesNotThrow(
					() -> userFileDAO.createUser(admin_user),
					"Unexpected exception thrown"
				);

        // Analyze
        assertNull(result);
    }

    /**
     * @author Ryan Lembo-Ehms
     */
    @Test
    public void testUpdateUser() {
        // Setup
        User user = new User(11, "Galactic Agent",  "ig");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getId());
        assertEquals(actual,user);
    }

    /**
     * @author Ethan Battaglia
     */
    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(
                                    any(File.class),
                                    any(User[].class)
                                );

        User user = new User(102, "The Rock Johnson",  "dwayne123");

        assertThrows(
                    IOException.class,
                    () -> userFileDAO.createUser(user),
                    "IOException not thrown"
                );
    }

		/**
		 * @author Alex Carron
		 */
    @Test
    public void testGetUserNotFound() {
        // Invoke
        User user = userFileDAO.getUser(98);

        // Analyze
        assertEquals(user, null);
    }

    /**
     * @author Ethan Battaglia
     */
    @Test
    public void testDeleteUserNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(userFileDAO.users.size(),testUsers.length);
    }

    /**
     * @author Ryan Lembo-Ehms
     */
    @Test
    public void testUpdateUserNotFound() {
        // Setup
        User user = new User(98, "Bolt",  "igneous");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    /**
     * @author Ethan Battaglia
     */
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the RockFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for User object persistence
 *
 * @author Victor Rabinovich
 */
public interface UserDAO {
    /**
     * Retrieves all {@linkplain User users}
     *
     * @return An array of {@link User user} objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Finds all {@linkplain User users} whose name contains the given text
     *
     * @param containsText The text to match against
     *
     * @return An array of {@link User users} whose nemes contains the given text, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain User user} with the given id
     *
     * @param id The id of the {@link User user} to get
     *
     * @return a {@link User user} object with the matching id
     * <br>
     * null if no {@link User user} with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain User user}
     *
     * @param user {@linkplain User user} object to be created and saved
     * <br>
     * The id of the user object is ignored and a new uniqe id is assigned
     *
     * @return new {@link User user} if successful, false otherwise
     *
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * Updates and saves a {@linkplain User user}
     *
     * @param {@link User user} object to be updated and saved
     *
     * @return updated {@link User user} if successful, null if
     * {@link User user} could not be found
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Deletes a {@linkplain User user} with the given id
     *
     * @param id The id of the {@link User user}
     *
     * @return true if the {@link User user} was deleted
     * <br>
     * false if user with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;

    /**
     * Deletes a {@linkplain User user} with the given id
     *
     * @param id The id of the {@link User user}
     *
     * @return true if the {@link User user} was deleted
     * <br>
     * false if user with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    String generateStrongUserPassword() throws IOException;
}

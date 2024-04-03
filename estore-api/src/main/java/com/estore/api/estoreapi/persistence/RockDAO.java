package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Rock;

/**
 * Defines the interface for Rock object persistence
 *
 * @author SWEN Faculty
 */
public interface RockDAO {
    /**
     * Retrieves all {@linkplain Rock rocks}
     *
     * @return An array of {@link Rock rock} objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    Rock[] getRocks() throws IOException;

    /**
     * Retrieves all {@linkplain Rock rocks}
     *
     * @return An array of {@link Rock rock} objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    boolean removeStockRocks(int[] itemIds) throws IOException;
    
    /**
     * Finds all {@linkplain Rock rocks} whose name contains the given text
     *
     * @param containsText The text to match against
     *
     * @return An array of {@link Rock rocks} whose nemes contains the given text, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    Rock[] findRocks(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Rock rock} with the given id
     *
     * @param id The id of the {@link Rock rock} to get
     *
     * @return a {@link Rock rock} object with the matching id
     * <br>
     * null if no {@link Rock rock} with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    Rock getRock(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Rock rock}
     *
     * @param rock {@linkplain Rock rock} object to be created and saved
     * <br>
     * The id of the rock object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Rock rock} if successful, false otherwise
     *
     * @throws IOException if an issue with underlying storage
     */
    Rock createRock(Rock rock) throws IOException;

    /**
     * Updates and saves a {@linkplain Rock rock}
     *
     * @param {@link Rock rock} object to be updated and saved
     *
     * @return updated {@link Rock rock} if successful, null if
     * {@link Rock rock} could not be found
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    Rock updateRock(Rock rock) throws IOException;

    /**
     * Deletes a {@linkplain Rock rock} with the given id
     *
     * @param id The id of the {@link Rock rock}
     *
     * @return true if the {@link Rock rock} was deleted
     * <br>
     * false if rock with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteRock(int id) throws IOException;
}

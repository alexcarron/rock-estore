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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Rock File DAO class
 *
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class RockFileDAOTest {
    RockFileDAO rockFileDAO;
    Rock[] testRocks;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupRockFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testRocks = new Rock[3];
        testRocks[0] = new Rock(99, "Wi-Fire",  "igneous", 10, 25, "spherical", "A rock");
        testRocks[1] = new Rock(100, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock");
        testRocks[2] = new Rock(101, "Ice Gladiator",  "igneous", 10, 25, "spherical", "A rock");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the rock array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Rock[].class))
                .thenReturn(testRocks);
        rockFileDAO = new RockFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetRocks() {
        // Invoke
        Rock[] rocks = rockFileDAO.getRocks();

        // Analyze
        assertEquals(rocks.length,testRocks.length);
        for (int i = 0; i < testRocks.length;++i)
            assertEquals(rocks[i],testRocks[i]);
    }

    @Test
    public void testFindRocks() {
        // Invoke
        Rock[] rocks = rockFileDAO.findRocks("la");

        // Analyze
        assertEquals(rocks.length,2);
        assertEquals(rocks[0],testRocks[1]);
        assertEquals(rocks[1],testRocks[2]);
    }

    @Test
    public void testGetRock() {
        // Invoke
        Rock rock = rockFileDAO.getRock(99);

        // Analzye
        assertEquals(rock,testRocks[0]);
    }

    @Test
    public void testDeleteRock() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> rockFileDAO.deleteRock(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test rocks array - 1 (because of the delete)
        // Because rocks attribute of RockFileDAO is package private
        // we can access it directly
        assertEquals(rockFileDAO.rocks.size(),testRocks.length-1);
    }

    @Test
    public void testCreateRock() {
        // Setup
        Rock rock = new Rock(102, "Wonder-Person",  "igneous", 10, 25, "spherical", "A rock");

        // Invoke
        Rock result = assertDoesNotThrow(() -> rockFileDAO.createRock(rock),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Rock actual = rockFileDAO.getRock(rock.getId());
        assertEquals(actual.getId(),rock.getId());
        assertEquals(actual.getName(),rock.getName());
    }

    @Test
    public void testUpdateRock() {
        // Setup
        Rock rock = new Rock(99, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock");

        // Invoke
        Rock result = assertDoesNotThrow(() -> rockFileDAO.updateRock(rock),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Rock actual = rockFileDAO.getRock(rock.getId());
        assertEquals(actual,rock);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(
									any(File.class),
									any(Rock[].class)
								);

        Rock rock = new Rock(102, "Rock",  "igneous", 10, 25, "spherical", "A rock");

        assertThrows(
					IOException.class,
					() -> rockFileDAO.createRock(rock),
					"IOException not thrown"
				);
    }

    @Test
    public void testGetRockNotFound() {
        // Invoke
        Rock rock = rockFileDAO.getRock(98);

        // Analyze
        assertEquals(rock,null);
    }

    @Test
    public void testDeleteRockNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> rockFileDAO.deleteRock(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(rockFileDAO.rocks.size(),testRocks.length);
    }

    @Test
    public void testUpdateRockNotFound() {
        // Setup
        Rock rock = new Rock(98, "Bolt",  "igneous", 10, 25, "spherical", "A rock");

        // Invoke
        Rock result = assertDoesNotThrow(() -> rockFileDAO.updateRock(rock),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

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
                .readValue(new File("doesnt_matter.txt"),Rock[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new RockFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

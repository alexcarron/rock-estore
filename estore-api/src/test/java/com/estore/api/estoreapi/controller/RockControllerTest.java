package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.RockDAO;
import com.estore.api.estoreapi.model.Rock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Rock Controller class
 *
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class RockControllerTest {
    private RockController rockController;
    private RockDAO mockRockDAO;

    /**
     * Before each test, create a new RockController object and inject
     * a mock Rock DAO
     */
    @BeforeEach
    public void setupRockController() {
        mockRockDAO = mock(RockDAO.class);
        rockController = new RockController(mockRockDAO);
    }

    @Test
    public void testGetRock() throws IOException {  // getRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // When the same id is passed in, our mock Rock DAO will return the Rock object
        when(mockRockDAO.getRock(rock.getId())).thenReturn(rock);

        // Invoke
        ResponseEntity<Rock> response = rockController.getRock(rock.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rock,response.getBody());
    }

    @Test
    public void testGetRockNotFound() throws Exception { // createRock may throw IOException
        // Setup
        int rockId = 99;
        // When the same id is passed in, our mock Rock DAO will return null, simulating
        // no rock found
        when(mockRockDAO.getRock(rockId)).thenReturn(null);

        // Invoke
        ResponseEntity<Rock> response = rockController.getRock(rockId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetRockHandleException() throws Exception { // createRock may throw IOException
        // Setup
        int rockId = 99;
        // When getRock is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).getRock(rockId);

        // Invoke
        ResponseEntity<Rock> response = rockController.getRock(rockId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all RockController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateRock() throws IOException {  // createRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Wi-Fire", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // when createRock is called, return true simulating successful
        // creation and save
        when(mockRockDAO.createRock(rock)).thenReturn(rock);

        // Invoke
        ResponseEntity<Rock> response = rockController.createRock(rock);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(rock,response.getBody());
    }

    @Test
    public void testCreateRockFailed() throws IOException {  // createRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Bolt", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // when createRock is called, return false simulating failed
        // creation and save
        when(mockRockDAO.createRock(rock)).thenReturn(null);

        // Invoke
        ResponseEntity<Rock> response = rockController.createRock(rock);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateRockHandleException() throws IOException {  // createRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Ice Gladiator", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);

        // When createRock is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).createRock(rock);

        // Invoke
        ResponseEntity<Rock> response = rockController.createRock(rock);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateRock() throws IOException { // updateRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Wi-Fire", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // when updateRock is called, return true simulating successful
        // update and save
        when(mockRockDAO.updateRock(rock)).thenReturn(rock);
        ResponseEntity<Rock> response = rockController.updateRock(rock);
        rock.setName("Bolt");

        // Invoke
        response = rockController.updateRock(rock);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rock,response.getBody());
    }

    @Test
    public void testUpdateRockFailed() throws IOException { // updateRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // when updateRock is called, return true simulating successful
        // update and save
        when(mockRockDAO.updateRock(rock)).thenReturn(null);

        // Invoke
        ResponseEntity<Rock> response = rockController.updateRock(rock);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateRockHandleException() throws IOException { // updateRock may throw IOException
        // Setup
        Rock rock = new Rock(99,"Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        // When updateRock is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).updateRock(rock);

        // Invoke
        ResponseEntity<Rock> response = rockController.updateRock(rock);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetRocks() throws IOException { // getRocks may throw IOException
        // Setup
        Rock[] rocks = new Rock[2];
        rocks[0] = new Rock(99,"Bolt", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        rocks[1] = new Rock(100,"The Great Iguana", "igneous", 10, 25, "spherical", "A rock", "rock.png", 92);
        // When getRocks is called return the rocks created above
        when(mockRockDAO.getRocks()).thenReturn(rocks);

        // Invoke
        ResponseEntity<Rock[]> response = rockController.getRocks();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rocks,response.getBody());
    }

    @Test
    public void testGetRocksHandleException() throws IOException { // getRocks may throw IOException
        // Setup
        // When getRocks is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).getRocks();

        // Invoke
        ResponseEntity<Rock[]> response = rockController.getRocks();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchRocks() throws IOException { // findRocks may throw IOException
        // Setup
        String searchString = "la";
        Rock[] rocks = new Rock[2];
        rocks[0] = new Rock(99,"Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        rocks[1] = new Rock(100,"Ice Gladiator", "igneous", 10, 25, "spherical", "A rock", "rock.png", 92);
        // When findRocks is called with the search string, return the two
        /// rocks above
        when(mockRockDAO.findRocks(searchString)).thenReturn(rocks);

        // Invoke
        ResponseEntity<Rock[]> response = rockController.searchRocks(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rocks,response.getBody());
    }

    @Test
    public void testSearchRocksHandleException() throws IOException { // findRocks may throw IOException
        // Setup
        String searchString = "an";
        // When createRock is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).findRocks(searchString);

        // Invoke
        ResponseEntity<Rock[]> response = rockController.searchRocks(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteRock() throws IOException { // deleteRock may throw IOException
        // Setup
        int rockId = 99;
        // when deleteRock is called return true, simulating successful deletion
        when(mockRockDAO.deleteRock(rockId)).thenReturn(true);

        // Invoke
        ResponseEntity<Rock> response = rockController.deleteRock(rockId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteRockNotFound() throws IOException { // deleteRock may throw IOException
        // Setup
        int rockId = 99;
        // when deleteRock is called return false, simulating failed deletion
        when(mockRockDAO.deleteRock(rockId)).thenReturn(false);

        // Invoke
        ResponseEntity<Rock> response = rockController.deleteRock(rockId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteRockHandleException() throws IOException { // deleteRock may throw IOException
        // Setup
        int rockId = 99;
        // When deleteRock is called on the Mock Rock DAO, throw an IOException
        doThrow(new IOException()).when(mockRockDAO).deleteRock(rockId);

        // Invoke
        ResponseEntity<Rock> response = rockController.deleteRock(rockId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}

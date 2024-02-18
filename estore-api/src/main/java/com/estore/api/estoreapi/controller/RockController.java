package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.RockDAO;
import com.estore.api.estoreapi.model.Rock;

/**
 * Handles the REST API requests for the Rock resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("rocks")
public class RockController {
    private static final Logger LOG = Logger.getLogger(RockController.class.getName());
    private RockDAO rockDao;

    /**
     * Creates a REST API controller to reponds to requests
     *
     * @param rockDao The {@link RockDAO Rock Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public RockController(RockDAO rockDao) {
        this.rockDao = rockDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Rock rock} for the given id
     *
     * @param id The id used to locate the {@link Rock rock}
     *
     * @return ResponseEntity with {@link Rock rock} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rock> getRock(@PathVariable int id) {
        LOG.info("GET /rocks/" + id);
        try {
            Rock rock = rockDao.getRock(id);
            if (rock != null)
                return new ResponseEntity<Rock>(rock,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Rock rocks}
     *
     * @return ResponseEntity with array of {@link Rock rock} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Rock[]> getRocks() {
        LOG.info("GET /rocks");

        try {
            Rock[] rocksArray = rockDao.getRocks();
            return new ResponseEntity<>(rocksArray, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Rock rocks} whose name contains
     * the text in name
     *
     * @param name The name parameter which contains the text used to find the {@link Rock rocks}
     *
     * @return ResponseEntity with array of {@link Rock rock} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all rocks that contain the text "ma"
     * GET http://localhost:8080/rocks/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Rock[]> searchRocks(@RequestParam String name) {
        LOG.info("GET /rocks/?name="+name);

        try {
            Rock[] filteredRocks = rockDao.findRocks(name);
            return new ResponseEntity<>(filteredRocks, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Rock rock} with the provided rock object
     *
     * @param rock - The {@link Rock rock} to create
     *
     * @return ResponseEntity with created {@link Rock rock} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Rock rock} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Rock> createRock(@RequestBody Rock rock) {
        LOG.info("POST /rocks " + rock);

        try {
            Rock newRock = rockDao.createRock(rock);
            if (newRock != null){
                Rock[] existingRocks = rockDao.findRocks(newRock.getName());
                for( Rock r : existingRocks){
                    if(r.getName().equals(newRock.getName())){
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
                return new ResponseEntity<Rock>(newRock,HttpStatus.CREATED);
            }else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Rock rock} with the provided {@linkplain Rock rock} object, if it exists
     *
     * @param rock The {@link Rock rock} to update
     *
     * @return ResponseEntity with updated {@link Rock rock} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Rock> updateRock(@RequestBody Rock rock) {
        LOG.info("PUT /rocks " + rock);

        try {
            Rock updatedRock = rockDao.updateRock(rock);
            if (updatedRock != null) {
                return new ResponseEntity<Rock>(updatedRock, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<Rock>(HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Rock rock} with the given id
     *
     * @param id The id of the {@link Rock rock} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Rock> deleteRock(@PathVariable int id) {
        LOG.info("DELETE /rocks/" + id);
        try {
            if (rockDao.deleteRock(id))
                return new ResponseEntity<Rock>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

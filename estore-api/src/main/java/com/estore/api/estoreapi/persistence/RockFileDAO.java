package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Rock;

/**
 * Implements the functionality for JSON file-based peristance for Rocks
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 */
@Component
public class RockFileDAO implements RockDAO {
    private static final Logger LOG = Logger.getLogger(RockFileDAO.class.getName());
    Map<Integer,Rock> rocks;   // Provides a local cache of the rock objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Rock
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new rock
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Rock File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     *
     * @throws IOException when file cannot be accessed or read from
     */
    public RockFileDAO(@Value("${rocks.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the rocks from the file
    }

    /**
     * Generates the next id for a new {@linkplain Rock rock}
     *
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Rock rocks} from the tree map
     *
     * @return  The array of {@link Rock rocks}, may be empty
     */
    private Rock[] getRocksArray() {
        return getRocksArray(null);
    }

    /**
     * Generates an array of {@linkplain Rock rocks} from the tree map for any
     * {@linkplain Rock rocks} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Rock rocks}
     * in the tree map
     *
     * @return  The array of {@link Rock rocks}, may be empty
     */
    private Rock[] getRocksArray(String containsText) { // if containsText == null, no filter
        ArrayList<Rock> rockArrayList = new ArrayList<>();

        for (Rock rock : rocks.values()) {
            if (containsText == null || rock.getName().contains(containsText)) {
                rockArrayList.add(rock);
            }
        }

        Rock[] rockArray = new Rock[rockArrayList.size()];
        rockArrayList.toArray(rockArray);
        return rockArray;
    }

    /**
     * Saves the {@linkplain Rock rocks} from the map into the file as an array of JSON objects
     *
     * @return true if the {@link Rock rocks} were written successfully
     *
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Rock[] rockArray = getRocksArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),rockArray);
        return true;
    }

    /**
     * Loads {@linkplain Rock rocks} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     *
     * @return true if the file was read successfully
     *
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        rocks = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of rocks
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Rock[] rockArray = objectMapper.readValue(new File(filename),Rock[].class);

        // Add each rock to the tree map and keep track of the greatest id
        for (Rock rock : rockArray) {
            rocks.put(rock.getId(),rock);
            if (rock.getId() > nextId)
                nextId = rock.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rock[] getRocks() {
        synchronized(rocks) {
            return getRocksArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rock[] findRocks(String containsText) {
        synchronized(rocks) {
            return getRocksArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rock getRock(int id) {
        synchronized(rocks) {
            if (rocks.containsKey(id))
                return rocks.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rock createRock(Rock rock) throws IOException {
        synchronized(rocks) {
            // We create a new rock object because the id field is immutable
            // and we need to assign the next unique id
            Rock newRock = new Rock(nextId(),rock.getName(), rock.getPrice(), rock.getType(), 
                        rock.getSize(), rock.getShape(), rock.getDescription());
            Rock[] existingRocks = findRocks(newRock.getName());
            if(existingRocks.length > 0) {
                return null;
            }
            rocks.put(newRock.getId(),newRock);
            save(); // may throw an IOException
            return newRock;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rock updateRock(Rock rock) throws IOException {
        synchronized(rocks) {
            if (rocks.containsKey(rock.getId()) == false)
                return null;  // rock does not exist

            rocks.put(rock.getId(),rock);
            save(); // may throw an IOException
            return rock;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteRock(int id) throws IOException {
        synchronized(rocks) {
            if (rocks.containsKey(id)) {
                rocks.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}

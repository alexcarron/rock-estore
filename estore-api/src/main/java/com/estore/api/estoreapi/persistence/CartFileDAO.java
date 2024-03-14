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

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

/**
 * Implements the functionality for JSON file-based peristance for Carts
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Ryan Lembo-Ehms
 */
@Component
public class CartFileDAO implements CartDAO{
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());
    Map<Integer,Cart> carts;   // Provides a local cache of the cart objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Cart
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Cart File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     *
     * @throws IOException when file cannot be accessed or read from
     */
    public CartFileDAO(@Value("${cart.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }

    /**
     * Loads {@linkplain Cart cart} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     *
     * @return true if the file was read successfully
     *
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        carts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Cart[] cartArray = objectMapper.readValue(new File(filename),Cart[].class);

        // Add each cart to the tree map
        for (Cart cart: cartArray) {
            carts.put(cart.getId(),cart);
        }
        
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart getCart(int id) {
        synchronized(carts) {
            if (carts.containsKey(id))
                return carts.get(id);
            else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Rock[] getRocksFromCart(int[] rockIds) throws IOException {
        RockFileDAO rockDAO = new RockFileDAO("data/rocks.json",objectMapper);
        ArrayList<Rock> rockArrayList = new ArrayList<Rock>();
        
        for (int rockId : rockIds) {
            Rock rock = rockDAO.getRock(rockId);
            if (rock != null) {
                rockArrayList.add(rock);
            }
        }

        Rock[] rockArray = new Rock[rockArrayList.size()];
        rockArrayList.toArray(rockArray);
        return rockArray;
    }

    // FOR TESTING PURPOSES //
    /**
     * Generates an array of {@linkplain Rock rocks} from the tree map for any
     * {@linkplain Rock rocks} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Rock rocks}
     * in the tree map
     *
     * @return  The array of {@link Rock rocks}, may be empty
     */
    private Cart[] getCartsArray() { // if containsText == null, no filter
        ArrayList<Cart> cartArrayList = new ArrayList<>();

        for (Cart cart : carts.values()) {
            cartArrayList.add(cart);
        }

        Cart[] cartArray = new Cart[cartArrayList.size()];
        cartArrayList.toArray(cartArray);
        return cartArray;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart[] getCarts() {
        synchronized(carts) {
            return getCartsArray();
        }
    }
}

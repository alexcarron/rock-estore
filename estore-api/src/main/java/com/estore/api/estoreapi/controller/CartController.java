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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.CartDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

/**
 * Handles the REST API requests for the Cart resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Ryan Lembo-Ehms, Ethan Battaglia
 */

 @RestController
 @RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private ObjectMapper objectMapper;
    private CartDAO cartDao;

    /**
     * Creates a REST API controller to reponds to requests
     *
     * @param cartDao The {@link CartDAO Cart Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CartController(CartDAO cartDao, ObjectMapper objectMapper) {
        this.cartDao = cartDao;
        this.objectMapper = objectMapper;
    }

    /**
     * Responds to the GET request for a {@linkplain Cart cart} for the given id
     *
     * @param id The id used to locate the {@link Cart cart}
     *
     * @return ResponseEntity with {@link Cart cart} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rock[]> getItemsFromCart(@PathVariable int id) {
        LOG.info("GET /cart/" + id);
        try {
            Cart cart = cartDao.getCart(id);
            if (cart != null) {
                Rock[] rocksArray = cartDao.getRocksFromCart(cart);
                return new ResponseEntity<Rock[]>(rocksArray,HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Cart carts}
     *
     * @return ResponseEntity with array of {@link Cart cart} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Cart[]> getCarts() {
        LOG.info("GET /cart");

        try {
            Cart[] cartsArray = cartDao.getCarts();
            return new ResponseEntity<>(cartsArray, HttpStatus.OK);
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
    @PutMapping("")
    public ResponseEntity<Cart> updateCart(@RequestBody Map<String, Object> payload) {
        Rock rock = null;
        try {
            // Extract the rock_updating part of the payload and convert it to a Rock object
            if(payload.containsKey("rock_updating")) {
                LOG.info("found it!");

                Object rockUpdating = payload.get("rock_updating");
                String rockUpdatingStr = objectMapper.writeValueAsString(rockUpdating);

                LOG.info(rockUpdatingStr);
                rock = objectMapper.convertValue(rockUpdating, Rock.class);
            } else {
                return new ResponseEntity<>(HttpStatus.IM_USED);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        int userId = (int) payload.get("id");
        boolean adding = (boolean) payload.get("adding");

        LOG.info("PUT /cart " + rock + " " + userId + " " + adding);
        
        try {
            Cart updatedCart = adding
            ? cartDao.addItem(rock, userId)
            : cartDao.deleteItem(rock, userId);

            if (updatedCart != null)
                return new ResponseEntity<Cart>(updatedCart,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Rock rock} with the provided rock object
     *
     * @param rock - The {@link Rock rock} to create
     *
     * @return ResponseEntity with updated {@link Cart cart} and HTTP status of OK<br>
     * ResponseEntity with HTTP status of INSUFFICIENT_STORAGE if not enough {@link Rock rock}s exist in storage<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestBody Map<String, Object> payload) {
        int cartId = (int) payload.get("id");

        LOG.info("PUT /cart/clear " + cartId);
        
        try {
            Cart updatedCart = cartDao.clearCart(cartId);
            System.out.println(updatedCart);
            if (updatedCart != null) {
                LOG.info("SUCCESS UPDATING CART " + cartId);
                return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
            }
            else {
                LOG.info("FAILURE UPDATING CART " + cartId);
                return new ResponseEntity<Cart>(updatedCart, HttpStatus.INSUFFICIENT_STORAGE);
            }
        }
        catch(IOException e) {
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
    public ResponseEntity<Cart> addCart(@RequestBody int id) {
        LOG.info("POST /cart " + id);
        
        try {
            cartDao.addCart(id);
            Cart newCart = cartDao.getCart(id);

            if (newCart != null)
                return new ResponseEntity<Cart>(newCart,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

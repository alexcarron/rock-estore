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

import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

/**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Victor Rabinovich
 */

 @RestController
 @RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private CartDAO cartDao;

    /**
     * Creates a REST API controller to reponds to requests
     *
     * @param userDao The {@link CartDAO Cart Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CartController(CartDAO cartDao) {
        this.cartDao = cartDao;
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
                Rock[] rocksArray = cartDao.getRocksFromCart(cart.getItemIds());
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
}

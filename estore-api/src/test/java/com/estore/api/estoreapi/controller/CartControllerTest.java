package com.estore.api.estoreapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.estore.api.estoreapi.persistence.CartDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The unit test suite for the CartController class
 *
 * @author Ryan Lembo-Ehms
 */
@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDao;
    private ObjectMapper objectMapper;

    /**
     * Before each test, create a new Cart Controller object and
     * inject a mock Cart DAO
     */
    @BeforeEach
    void setUp() {
        mockCartDao = Mockito.mock(CartDAO.class);
        objectMapper = new ObjectMapper();
        cartController = new CartController(mockCartDao, objectMapper);
    }

    /**
     * Test the getItemsFromCart method
     * @throws IOException
     */
    @Test
    void testGetItemsFromCartFound() throws IOException {
        Rock[] rocks = {new Rock(1, "Rock1", "Type1", 1.0, 1.0, "Round", "Desc", "url", 0, "hat1", "clothes1")};
        Cart expectedCart = new Cart(1, rocks);

        when(mockCartDao.getCart(1)).thenReturn(expectedCart);
        when(mockCartDao.getRocksFromCart(expectedCart)).thenReturn(rocks);

        ResponseEntity<Rock[]> response = cartController.getItemsFromCart(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(rocks, response.getBody());
    }

    @Test
    void testGetItemsFromCartNotFound() throws IOException {
        when(mockCartDao.getCart(anyInt())).thenReturn(null);
        
        ResponseEntity<Rock[]> response = cartController.getItemsFromCart(999);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    /**
     * Test the getItemsFromCart method when an IOException is thrown
     */
    @Test
    public void testGetItemsFromCartIOException() throws IOException {
        // Setup
        int testCartId = -1;
        doThrow(new IOException()).when(mockCartDao).getCart(testCartId);

        // Invoke
        Rock[] actualRocks = cartController.getItemsFromCart(testCartId).getBody();

        // Assert
        assertNull(actualRocks);
    }

    @Test
    void testGetCartsSuccess() throws IOException {
        Cart[] carts = {new Cart(1, new Rock[0]), new Cart(2, new Rock[0])};
    
        when(mockCartDao.getCarts()).thenReturn(carts);
    
        ResponseEntity<Cart[]> response = cartController.getCarts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(carts, response.getBody());
    }

    @Test
    void testUpdateCartIOException() throws IOException {
        // Setup
        Map<String, Object> payload = new HashMap<>();
        Rock rock = new Rock(1, "Rock1", "Type1", 1.0, 1.0, "Round", "Desc", "url", 0, "hat1", "clothes1");
        payload.put("rock_updating", rock);
        payload.put("id", 1);
        payload.put("adding", true);

        when(mockCartDao.addItem(rock, 1)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    /*
    @Test
    void testUpdateCartAddingItemSuccess() throws IOException {
        Rock rockToAdd = new Rock(3, "Limestone", "sedimentary", 500, 6, "cube", "Original slab of limestone used to build the pyramids.", "limestone.png", "", "Blazer");
        Cart updatedCart = new Cart(1, new Rock[]{rockToAdd});
        when(mockCartDao.addItem(any(Rock.class), eq(1))).thenReturn(updatedCart);

        // Correctly convert Rock object to Map simulating JSON structure
        // Direct conversion to Map without needing to serialize to String first
        Map<String, Object> rockMap = objectMapper.readValue(objectMapper.writeValueAsString(rockToAdd), new TypeReference<Map<String, Object>>(){});

        Map<String, Object> payload = Map.of(
            "rock_updating", rockMap,
            "id", 1,
            "adding", true
        );

        // When
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedCart, response.getBody());
    }

    @Test
    public void testUpdateCartDeletingItem() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , "id", 10, "adding", false);
        Cart expectedCart = new Cart(10, new Rock[]{});
        when(mockCartDao.deleteItem(new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , 10)).thenReturn(expectedCart);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testUpdateCartNotFound() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , "id", 10, "adding", true);
        when(mockCartDao.addItem(new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , 10)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateCartIOException() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", new Rock(-1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , "id", 10, "adding", true);
        doThrow(new IOException()).when(mockCartDao).addItem(new Rock(-1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        , 10);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
*/
    @Test
    public void testAddCart() throws IOException {
        // Setup
        int id = 10;
        Cart expectedCart = new Cart(id, new Rock[]{});
        when(mockCartDao.getCart(id)).thenReturn(expectedCart);

        // Invoke
        ResponseEntity<Cart> response = cartController.addCart(id);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testAddCartNotFound() throws IOException {
        // Setup
        int id = 10;
        when(mockCartDao.getCart(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.addCart(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddCartIOException() throws IOException {
        // Setup
        int id = -1;
        doThrow(new IOException()).when(mockCartDao).getCart(id);

        // Invoke
        ResponseEntity<Cart> response = cartController.addCart(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCartsIOException() throws IOException {
        // Setup
        doThrow(new IOException("Database access error")).when(mockCartDao).getCarts();

        // Invoke
        ResponseEntity<Cart[]> response = cartController.getCarts();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "The response status should be INTERNAL_SERVER_ERROR when an IOException occurs.");
    }
}

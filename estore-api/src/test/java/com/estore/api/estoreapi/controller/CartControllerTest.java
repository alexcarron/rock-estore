package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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

    /**
     * Before each test, create a new Cart Controller object and
     * inject a mock Cart DAO
     */
    @BeforeEach
    public void setupCartController() {
        mockCartDao = mock(CartDAO.class);
        cartController = new CartController(mockCartDao);
    }

    /**
     * Test the getItemsFromCart method
     * @throws IOException
     */
    @Test
    public void testGetItemsFromCart() throws IOException {
        // Setup rocks
        Rock[] expectedRocks = new Rock[2];
        expectedRocks[0] = new Rock(99, "Wi-Fire",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 33);
        expectedRocks[1] = new Rock(100, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 67);

        // Set up cart
        int testCartId = 10;
        Cart testCart = new Cart(testCartId, new int[]{99, 100});
        when(mockCartDao.getCart(testCartId)).thenReturn(testCart);
        when(mockCartDao.getRocksFromCart(testCart)).thenReturn(expectedRocks);

        // Invoke
        Rock[] actualRocks = cartController.getItemsFromCart(testCartId).getBody();

        // Assert
        assertEquals(expectedRocks, actualRocks);
    }

    /**
     * Test the getItemsFromCart method when the cart is not found
     */
    @Test
    public void testGetItemsFromCartNotFound() throws IOException {
        // Setup
        int testCartId = 10;
        when(mockCartDao.getCart(testCartId)).thenReturn(null);

        // Invoke
        Rock[] actualRocks = cartController.getItemsFromCart(testCartId).getBody();

        // Assert
        assertNull(actualRocks);
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

    /**
     * Test the getCarts method
     */
    @Test
    public void testGetCarts() throws IOException{
        // Setup carts
        Cart[] expected_carts = new Cart[3];
        expected_carts[0] = new Cart(10, new int[]{1,2,3});
        expected_carts[1] = new Cart(11, new int[]{6,7,8});
        expected_carts[2] = new Cart(12, new int[]{99,100,101});
        when(mockCartDao.getCarts()).thenReturn(expected_carts);

        // Invoke
        Cart[] actual_carts = cartController.getCarts().getBody();

        // Assert
        assertEquals(expected_carts, actual_carts);
    }

    @Test
    public void testUpdateCartAddingItem() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", 1, "id", 10, "adding", true);
        Cart expectedCart = new Cart(10, new int[]{1});
        when(mockCartDao.addItem(1, 10)).thenReturn(expectedCart);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testUpdateCartDeletingItem() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", 1, "id", 10, "adding", false);
        Cart expectedCart = new Cart(10, new int[]{});
        when(mockCartDao.deleteItem(1, 10)).thenReturn(expectedCart);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testUpdateCartNotFound() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", 1, "id", 10, "adding", true);
        when(mockCartDao.addItem(1, 10)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateCartIOException() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("rock_updating", -1, "id", 10, "adding", true);
        doThrow(new IOException()).when(mockCartDao).addItem(-1, 10);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(payload);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testClearCart() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("id", 10);
        Cart expectedCart = new Cart(10, new int[]{});
        when(mockCartDao.clearCart(10)).thenReturn(expectedCart);

        // Invoke
        ResponseEntity<Cart> response = cartController.clearCart(payload);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testClearCartNotFound() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("id", 10);
        when(mockCartDao.clearCart(10)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.clearCart(payload);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testClearCartIOException() throws IOException {
        // Setup
        Map<String, Object> payload = Map.of("id", 10);
        when(mockCartDao.clearCart(10)).thenThrow(IOException.class);

        // Invoke
        ResponseEntity<Cart> response = cartController.clearCart(payload);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddCart() throws IOException {
        // Setup
        int id = 10;
        Cart expectedCart = new Cart(id, new int[]{});
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

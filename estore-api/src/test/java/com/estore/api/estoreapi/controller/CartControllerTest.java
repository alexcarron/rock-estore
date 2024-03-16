package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
        expectedRocks[0] = new Rock(99, "Wi-Fire",  "igneous", 10, 25, "spherical", "A rock");
        expectedRocks[1] = new Rock(100, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock");
        
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
}

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
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Cart File DAO class
 * 
 * @author Ryan Lembo-Ehms
 */
@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDao;
    Cart[] testCarts;
    Rock[] testRocks;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCarts = new Cart[3];
        testCarts[0] = new Cart(10, new int[]{1, 2, 3, 4, 5});
        testCarts[1] = new Cart(11, new int[]{6, 7, 8, 9, 10});
        testCarts[2] = new Cart(12, new int[]{99, 100, 101});

        testRocks = new Rock[3];
        testRocks[0] = new Rock(99, "Wi-Fire",  "igneous", 10, 25, "spherical", "A rock");
        testRocks[1] = new Rock(100, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock");
        testRocks[2] = new Rock(101, "Ice Gladiator",  "igneous", 10, 25, "spherical", "A rock");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the cart array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Cart[].class))
                .thenReturn(testCarts);
        cartFileDao = new CartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    /**
     * Test the getCart method
     */
    @Test
    public void testGetCart() {
        // Setup
        int expected_id = 10;

        // Invoke
        Cart cart = cartFileDao.getCart(expected_id);

        // Assert
        assertNotNull(cart);
        assertEquals(cart, testCarts[0]);
    }

    /**
     * Test the getCart method with a non-existent cart
     */
    @Test
    public void testGetCartNonExistent() {
        // Setup
        int expected_id = 100;

        // Invoke
        Cart cart = cartFileDao.getCart(expected_id);

        // Assert
        assertNull(cart);
    }
}

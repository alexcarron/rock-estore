package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

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
        testCarts[0] = new Cart(10, new Rock[]{
            new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        });
        testCarts[1] = new Cart(11, new Rock[]{
            new Rock(4, "Galactic bAgent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(5, "Galactic bage",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(6, "Galactic bnt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        });
        testCarts[2] = new Cart(12, new Rock[]{
            new Rock(7, "Galactic cAgent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(8, "Galactic cage",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(9, "Galactic cnt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        });

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

    /**
     * Test the getCarts method
     */
    @Test
    public void testGetCarts() {
        // Invoke
        Cart[] carts = cartFileDao.getCarts();

        // Analyze
        assertEquals(carts.length, testCarts.length);

        int index = 0;
        for (Cart cart : carts) {
            assertEquals(cart, testCarts[index]);
            index++;
        }
    }

/*
    @Test
    public void testGetRocksFromCart() throws IOException {
        // Setup: Define the rock IDs present in the test cart
        int[] rockIdsInCart = {1, 2, 3}; // IDs from the provided rocks.json
        Cart testCart = new Cart(10, rockIdsInCart); // Create a cart for testing
    
        // Expected Rocks based on rocks.json
        Rock[] expectedRocks = new Rock[]{
            new Rock(1, "Andesite", "igneous", 10, 2, "spherical", "Mined by Dwayne \"The Rock\" Johnson in Yosemite."),
            new Rock(2, "Obsidian", "igneous", 3, 0.5, "spherical", "Buy 10 to go to the nether!"),
            new Rock(3, "Limestone", "sedimentary", 500, 6, "cube", "Original slab of limestone used to build the pyramids.")
        };
    
        // Since RockFileDAO is instantiated directly in CartFileDAO, we cannot directly mock it.
        // This test will need to be an integration test that relies on the actual rocks.json file
        // being correctly read by RockFileDAO. This assumes RockFileDAO and ObjectMapper are working as expected.
        
        // Act: Retrieve rocks from cart
        Rock[] retrievedRocks = cartFileDao.getRocksFromCart(testCart);
    
        // Assert: Validate the retrieved rocks match the expected rocks
        assertNotNull(retrievedRocks, "Retrieved rocks should not be null.");
        assertEquals(expectedRocks.length, retrievedRocks.length, "The number of retrieved rocks should match the expected number.");
        
        // Additional assertions could be made here to compare the properties of each expected Rock object with the retrieved ones.
        // For simplicity, we're only checking the count.
    }
    


    @Test
    public void testAddItem() throws IOException {
        // Setup
        int userId = 10; // Assume this cart exists
        int rockIdToAdd = 999; // New rock id to add
        Cart expectedCart = new Cart(userId, new int[]{1, 2, 3, 4, 5, 999});
        testCarts[0].appendItem(999); // Adding rock id 999 to the first test cart

        doThrow(new IOException()).when(mockObjectMapper).writeValue(new File("doesnt_matter.txt"), any());

        // Invoke
        Cart resultCart = cartFileDao.addItem(rockIdToAdd, userId);

        // Assert
        assertEquals(expectedCart, resultCart, "The cart should have the new item added");
    }

    @Test
    public void testDeleteItem() throws IOException {
        // Setup
        int userId = 10; // Assume this cart exists
        int rockIdToDelete = 3; // Rock id to delete
        Cart expectedCart = new Cart(userId, new int[]{1, 2, 4, 5}); // Expected cart after deletion
        testCarts[0].removeItem(3); // Removing rock id 3 from the first test cart
        
        doThrow(new IOException()).when(mockObjectMapper).writeValue(new File("doesnt_matter.txt"), any());

        // Invoke
        Cart resultCart = cartFileDao.deleteItem(rockIdToDelete, userId);

        // Assert
        assertEquals(expectedCart, resultCart, "The cart should not have the deleted item");
    }

    @Test
    public void testAddCartNew() throws IOException {
        // Setup
        int newCartId = 999; // ID for the new cart
        Cart expectedCart = new Cart(newCartId, new int[0]); // Expected new empty cart

        doThrow(new IOException()).when(mockObjectMapper).writeValue(new File("doesnt_matter.txt"), any());

        // Invoke
        Cart resultCart = cartFileDao.addCart(newCartId);

        // Assert
        assertEquals(expectedCart, resultCart, "A new cart should be added with the specified ID");
    }
*/
    @Test
    public void testClearCartSuccess() throws IOException {

        // Setup
        Cart testCart = testCarts[0];
        int[] expectedIds = new int[0];

        // Invoke
        Cart resultCart = cartFileDao.clearCart(testCart.getId());
        int[] actualIds = resultCart.getItemIds();

        // Assert
        assertArrayEquals(expectedIds, actualIds);
    }
    
    @Test
    public void testClearCartNotFound() throws IOException {

        // Setup
        int testId = 4;

        // Invoke
        Cart resultCart = cartFileDao.clearCart(testId);

        // Assert
        assertNull(resultCart);
    }

    @Test
    public void testAddCartExisting() throws IOException {
        // Setup
        int existingCartId = 10; // Assume this cart exists

        // Invoke
        Cart resultCart = cartFileDao.addCart(existingCartId);

        // Assert
        assertNull(resultCart, "No new cart should be added if it already exists");
    }
}

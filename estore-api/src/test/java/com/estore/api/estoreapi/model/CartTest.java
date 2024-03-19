package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Cart class
 * 
 * @author Ryan Lembo-Ehms
 */
@Tag("Model-tier")
public class CartTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 53;
        int[] expected_item_ids = {1,2,3,4,5};

        // Invoke
        Cart cart = new Cart(expected_id, expected_item_ids);

        // Assert
        assertEquals(expected_id, cart.getId());
        assertEquals(expected_item_ids, cart.getItemIds());
    }

    @Test
    public void testAppend() {
        // Setup
        int expected_id = 53;
        int[] expected_item_ids = {1,2,3,4,5};

        int appended_id = 6;
        int[] append_item_ids = {1,2,3,4,5,6};

        // Invoke
        Cart cart = new Cart(expected_id, expected_item_ids);

        cart.appendItem(appended_id);
        
        // Assert
        assertArrayEquals(append_item_ids, cart.getItemIds());
    }

    @Test
    public void testRemove() {
        // Setup
        int expected_id = 53;
        int[] expected_item_ids = {1,2,3,4,3,5};

        int remove_id = 3;
        int[] remove_item_ids = {1,2,4,3,5};

        // Invoke
        Cart cart = new Cart(expected_id, expected_item_ids);

        cart.removeItem(remove_id);
        
        // Assert
        assertArrayEquals(remove_item_ids, cart.getItemIds());
        
    }


    @Test
    public void testToString() {
        // Setup
        int expected_id = 53;
        int[] expected_item_ids = {1,2,3,4,5};
        String expected_toString = "cart [id=53,item_ids=[1, 2, 3, 4, 5]]";

        // Invoke
        Cart cart = new Cart(expected_id, expected_item_ids);

        // Assert
        assertEquals(expected_toString, cart.toString());
    }
}

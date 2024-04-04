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
        Rock[] expected_rocks = {
            new Rock(1, "Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(2, "Galactic age", "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
            new Rock(3, "Galactic nt", "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")
        };

        // Invoke
        Cart cart = new Cart(expected_id, expected_rocks);

        // Assert
        assertEquals(expected_id, cart.getId());
        assertArrayEquals(expected_rocks, cart.getRocks());
    }

    @Test
    public void testAppend() {
        // Setup
        int expected_id = 53;
        Rock[] expected_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")};

        Rock appended_rock = new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt");
        Rock[] append_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")};


        // Invoke
        Cart cart = new Cart(expected_id, expected_rocks);

        cart.appendItem(appended_rock);
        
        // Assert
        assertArrayEquals(append_rocks, cart.getRocks());
    }

    @Test
    public void testRemove() {
        // Setup
        int expected_cart_id = 53;
        Rock[] expected_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")};

        Rock remove_rock = new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt");
        Rock[] remove_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", "", "shirt")};

        // Invoke
        Cart cart = new Cart(expected_cart_id, expected_rocks);

        cart.removeItem(remove_rock);
        
        // Assert
        assertArrayEquals(remove_rocks, cart.getRocks());
        
    }

/*
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
    */
}

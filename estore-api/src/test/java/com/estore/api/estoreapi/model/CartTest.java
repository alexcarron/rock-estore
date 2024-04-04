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
            new Rock(1, "Galactic Agent", "igneous", 10, 25, "spherical", "A rock", "rock.png", 1, "", "shirt"),
            new Rock(2, "Galactic age", "igneous", 10, 25, "spherical", "A rock", "rock.png", 2, "", "shirt"),
            new Rock(3, "Galactic nt", "igneous", 10, 25, "spherical", "A rock", "rock.png", 3, "", "shirt")
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
        Rock[] expected_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 1, "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 2, "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 3, "", "shirt")};

        Rock appended_rock = new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 4, "", "shirt");
        Rock[] append_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 1, "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 2, "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 3, "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 4, "", "shirt")};


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
        Rock[] expected_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 1, "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 2, "", "shirt"),
        new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 3, "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 4, "", "shirt")};

        Rock remove_rock = new Rock(3, "Galactic nt",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 3, "", "shirt");
        Rock[] remove_rocks = {new Rock(1, "Galactic Agent",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 1, "", "shirt"),
        new Rock(2, "Galactic age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 2, "", "shirt"),
        new Rock(4, "gal age",  "igneous", 10, 25, "spherical", "A rock", "rock.png", 4, "", "shirt")};

        // Invoke
        Cart cart = new Cart(expected_cart_id, expected_rocks);

        cart.removeItem(remove_rock);
        
        // Assert
        assertArrayEquals(remove_rocks, cart.getRocks());
        
    }

    @Test
    void testToString() {
        // Setup
        int cartId = 1;
        Rock[] rocksArray = {
            new Rock(1, "Rock1", "Type1", 1.0, 1.0, "Round", "Desc", "url", 0, "hat1", "clothes1"),
            new Rock(2, "Rock2", "Type2", 2.0, 2.0, "Square", "Desc2", "url2", 0, "hat2", "clothes2")
        };
        Cart cart = new Cart(cartId, rocksArray);

        // Expected string
        String expected = "Cart: 1 Rocks: Rock [id=1, name=Rock1, type=Type1, price=1.000000, size=1.000000, shape=Round, description=Desc, image_url=url, stock=0] Rock [id=2, name=Rock2, type=Type2, price=2.000000, size=2.000000, shape=Square, description=Desc2, image_url=url2, stock=0] ";

        // Invoke
        String actual = cart.toString();

        // Assert
        assertEquals(expected, actual);
    }


}

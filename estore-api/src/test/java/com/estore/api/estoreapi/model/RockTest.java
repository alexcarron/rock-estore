package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Rock class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class RockTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
				double expected_price = 10;
				String expected_type = "igneous";
				double expected_size = 25;
				String expected_shape = "spherical";
				String expected_description = "A rock";
				String expected_image_url = "media/images/rocks/andesite.png";
        int expected_stock = 33;

        // Invoke
        Rock rock = new Rock(
					expected_id,
					expected_name,
					expected_type,
					expected_price,
					expected_size,
					expected_shape,
					expected_description,
					expected_image_url,
          expected_stock
				);

        // Analyze
        assertEquals(expected_id, rock.getId());
        assertEquals(expected_name, rock.getName());
        assertEquals(expected_type, rock.getType());
        assertEquals(expected_size, rock.getSize());
        assertEquals(expected_shape, rock.getShape());
        assertEquals(expected_description, rock.getDescription());
        assertEquals(expected_image_url, rock.getImageUrl());
        assertEquals(expected_stock, rock.getStock());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
				double price = 10;
				String type = "igneous";
				double size = 25;
				String shape = "spherical";
				String description = "A rock";
				String image_url = "media/images/rocks/andesite.png";
        int stock = 33;


        // Invoke
        Rock rock = new Rock(id, name, type, price, size, shape, description, image_url, stock);


        String expected_name = "Galactic Agent";

        // Invoke
        rock.setName(expected_name);

        // Analyze
        assertEquals(expected_name,rock.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
				double price = 10;
				String type = "igneous";
				double size = 25;
				String shape = "spherical";
				String description = "A rock";
				String image_url = "media/images/rocks/andesite.png";
        int stock = 33;


        String expected_string = String.format(Rock.STRING_FORMAT, id, name, type, price, size, shape, description, image_url, stock);

        Rock rock = new Rock(id, name, type, price, size, shape, description, image_url, stock);

        // Invoke
        String actual_string = rock.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
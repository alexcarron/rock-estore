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

        // Invoke
        Rock rock = new Rock(expected_id,expected_name);

        // Analyze
        assertEquals(expected_id,rock.getId());
        assertEquals(expected_name,rock.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Rock rock = new Rock(id,name);

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
        String expected_string = String.format(Rock.STRING_FORMAT,id,name);
        Rock rock = new Rock(id,name);

        // Invoke
        String actual_string = rock.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}
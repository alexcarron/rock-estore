package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a cart entity
 *
 * @author Ryan Lembo-Ehms, Ethan Battaglia
 */
public class Cart {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "cart [id=%d,item_ids=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("rocks") private List<Rock> rocks;

    /**
     * Create a user cart with the given id and item ids
     * @param id The id of the cart corresponding to the user
     * @param item_ids The ids of the rocks in the cart

     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Cart(
        @JsonProperty("id") int id,
        @JsonProperty("rocks") Rock[] rocksArray
        ) {
        this.id = id;
        this.rocks = rocksArray != null ? new ArrayList<>(Arrays.asList(rocksArray)) : new ArrayList<>();
    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Retrieves the ids of the rocks in the cart
     * @return The ids of the rocks in the cart
     */
    public Rock[] getRocks() {return rocks.toArray(new Rock[0]);}

    /**
     * Appends itemId onto the item_ids list
     */
    public void appendItem(Rock newRock) {
        rocks.add(newRock);
    }

    /**
     * Removes the first instance of an itemId from the item_ids list
     */
    public void removeItem(Rock remRock) {
        rocks.remove(remRock);
    }
    

    public void clearItems() {
        rocks.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart [id=" + id + ", rocks=");
        rocks.forEach(rock -> sb.append(rock.toString()).append(", "));
        if (!rocks.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
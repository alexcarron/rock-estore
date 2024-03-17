package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import java.util.Arrays;

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
    @JsonProperty("item_ids") private int[] item_ids;

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
			@JsonProperty("item_ids") int[] item_ids
	    ) {
        this.id = id;
        this.item_ids = item_ids;
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
    public int[] getItemIds() {return item_ids;}

    /**
     * Appends an item onto the item_ids list
     * @return The ids of the rocks in the cart
     */
    public void appendItem(int itemId) {
        item_ids = Arrays.copyOf(item_ids, item_ids.length + 1);
        item_ids[item_ids.length - 1] = itemId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, Arrays.toString(item_ids));
    }
}
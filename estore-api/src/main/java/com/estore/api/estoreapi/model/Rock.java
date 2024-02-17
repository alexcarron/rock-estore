package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Rock entity
 *
 * @author SWEN Faculty
 */
public class Rock {
    private static final Logger LOG = Logger.getLogger(Rock.class.getRockType());

    // Package private for tests
    static final String STRING_FORMAT = "Rock [id=%d, type=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("price") private int price;
    @JsonProperty("type") private String type;
    @JsonProperty("size") private double size;
    @JsonProperty("shape") private String shape;
    @JsonProperty("description") private String description;

    /**
     * Create a rock with the given id, price, type, size, shape and description
     * @param id The id of the rock
     * @param price The price of the rock
     * @param type The type of the rock
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Rock(@JsonProperty("id") int id, @JsonProperty("price") int price, @JsonProperty("type") String type,
                @JsonProperty("size") double size, @JsonProperty("shape") String shape, @JsonProperty("description") String description) {
        this.id = id;
        this.price = price;
        this.type = type;
        this.size = size;
        this.shape = shape;
        this.description = description;
    }

    /**
     * Retrieves the id of the rock
     * @return The id of the rock
     */
    public int getId() {return id;}

    /**
     * Sets the type of the rock - necessary for JSON object to Java object deserialization
     * @param type The type of the rock
     */
    public void setType(String type) {this.type = type;}

    /**
     * Retrieves the type of the rock
     * @return The type of the rock
     */
    public String getRockType() {return type;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,type);
    }
}
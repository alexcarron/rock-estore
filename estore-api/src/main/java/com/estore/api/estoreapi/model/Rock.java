package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Rock entity
 *
 * @author SWEN Faculty
 */
public class Rock {
    private static final Logger LOG = Logger.getLogger(Rock.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Rock [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;

    /**
     * Create a rock with the given id and name
     * @param id The id of the rock
     * @param name The name of the rock
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Rock(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the id of the rock
     * @return The id of the rock
     */
    public int getId() {return id;}

    /**
     * Sets the name of the rock - necessary for JSON object to Java object deserialization
     * @param name The name of the rock
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the rock
     * @return The name of the rock
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}
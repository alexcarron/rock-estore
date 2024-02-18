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
    static final String STRING_FORMAT = "Rock [id=%d, name=%s, type=%s, price=%f, size=%f, shape=%s, description=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("type") private String type;
    @JsonProperty("price") private double price;
    @JsonProperty("size") private double size;
    @JsonProperty("shape") private String shape;
    @JsonProperty("description") private String description;

    /**
     * Create a rock with the given id, price, type, size, shape and description
     * @param id The id of the rock
     * @param name The name of the rock
     * @param type The type of the rock
     * @param price The price of the rock
     * @param size The size of the rock in feet
     * @param shape The shape of the rock
     * @param description The description of the rock
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Rock(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("type") String type,
                @JsonProperty("size") double size, @JsonProperty("shape") String shape, @JsonProperty("description") String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
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
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the rock
     * @return The name of the rock
     */
    public String getName() {return this.name;}

    /**
     * Gets the price for the rock
     * @return The rock's price
     */
     public double getPrice() {return this.price;}

     /**
      * Gets the rock type, i.e igneous, sedimentary, metamorphic
      * @return The rock type
      */
     public String getType() {return this.type;}

     /**
      * Gets the size of the rock
      * @return The rock's size
      */
     public double getSize() {return this.size;}

     /**
      * Get the rock's shape
      * @return The shape of the rock
      */
     public String getShape() {return this.shape;}

    /**
     * Gets the product description of the rock
     * @return The rock's produt description
     */
     public String getDescription() {return this.description;}


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,type,price,size,shape,description);
    }
}
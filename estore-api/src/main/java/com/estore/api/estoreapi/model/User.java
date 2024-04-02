package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a user entity
 *
 * @author Victor Rabinovich
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "user [id=%d, username=%s, password=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    /**
     * Create a user with the given id, price, type, size, shape and description
     * @param id The id of the user
     * @param username The username of the user
     * @param password The type of the user

     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(
			@JsonProperty("id") int id,
			@JsonProperty("username") String username,
			@JsonProperty("password") String password
	    ) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Sets the name of the user - necessary for JSON object to Java object deserialization
     * @param username The type of the user
     */
    public void setName(String username) {this.username = username;}

    /**
     * Sets the name of the user - necessary for JSON object to Java object deserialization
     * @param password The type of the user
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getName() {return this.username;}


     /**
      * Gets the user password
      * @return The user type
      */
     public String getPassword() {return this.password;}

     
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,username,password);
    }
}
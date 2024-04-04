package com.estore.api.estoreapi.model;

import java.util.Objects;
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
    static final String STRING_FORMAT = "Rock [id=%d, name=%s, type=%s, price=%f, size=%f, shape=%s, description=%s, image_url=%s, stock=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("type") private String type;
    @JsonProperty("price") private double price;
    @JsonProperty("size") private double size;
    @JsonProperty("shape") private String shape;
    @JsonProperty("description") private String description;
    @JsonProperty("image_url") private String image_url;
    @JsonProperty("stock") private int stock;
    @JsonProperty("custom_hat") private String custom_hat;
    @JsonProperty("custom_clothes") private String custom_clothes;

    /**
     * Create a rock with the given id, price, type, size, shape, description, and image_url
     * @param id The id of the rock
     * @param name The name of the rock
     * @param type The type of the rock
     * @param price The price of the rock
     * @param size The size of the rock in feet
     * @param shape The shape of the rock
     * @param description The description of the rock
     * @param image_url The url to the image of the rock
     * @param custom_hat The name of custom hat
     * @param custom_clothes The name of custom clothes
     * @param stock the total available count of the rock
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Rock(
			@JsonProperty("id") int id,
			@JsonProperty("name") String name,
			@JsonProperty("type") String type,
			@JsonProperty("price") double price,
			@JsonProperty("size") double size,
			@JsonProperty("shape") String shape,
			@JsonProperty("description") String description,
			@JsonProperty("image_url") String image_url,
            @JsonProperty("stock") int stock,
            @JsonProperty("custom_hat") String custom_hat,
            @JsonProperty("custom_clothes") String custom_clothes
		) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.shape = shape;
        this.description = description;
        this.image_url = image_url;
        this.stock = stock;
        this.custom_hat = custom_hat;
        this.custom_clothes = custom_clothes;
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
     * Gets the image url of the rock
     * @return The rock's image url
     */
     public String get_image_url() {return this.image_url;}

     /**
     * Gets the name of the custom hat
     * @return The custom hat
     */
    public String get_custom_hat() {return this.custom_hat;}

    /**
     * Gets the name of the custom clothes
     * @return The custom clothes
     */
    public String get_custom_clothes() {return this.custom_clothes;}

     /**
     * Gets the stock of the rock
     * @return The rock's stock
     */
    public int getStock() {return this.stock;}

    /**
     * Removes one from the stock of this rock
     */
    public void removeStock() {this.stock--;}



    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,type,price,size,shape,description,image_url,stock);
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Rock rock = (Rock) obj;
    return id == rock.id &&
        Double.compare(rock.price, price) == 0 &&
        Double.compare(rock.size, size) == 0 &&
        name.equals(rock.name) &&
        type.equals(rock.type) &&
        shape.equals(rock.shape) &&
        description.equals(rock.description) &&
        image_url.equals(rock.image_url) &&
        Objects.equals(custom_hat, rock.custom_hat) &&  // Use Objects.equals for potential nulls
        Objects.equals(custom_clothes, rock.custom_clothes);
    }
}
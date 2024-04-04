package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Rock;

/**
 * Defines the interface for Cart object persistence
 *
 * @author Ryan Lembo-Ehms, Ethan Battaglia
 */
public interface CartDAO {
    /**
     * Retrieves a {@linkplain Cart cart} with the given id
     *
     * @param id The id of the {@link Cart cart} to get
     *
     * @return a {@link Cart cart} object with the matching id
     * <br>
     * null if no {@link Cart cart} with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    Cart getCart(int id) throws IOException;

    /**
     * Adds and saves a {@linkplain Rock newRock} to the cart
     *
     * @param newRock {@linkplain Rock newRock} id of the rock to be added
     *  
     * @param userId {@linkplain int userId} id of the user's cart that
     * the rock will be added to
     *
     * @return updated {@link Cart cart} with matching id to userId
     *
     * @throws IOException if an issue with underlying storage
     */
    Cart addItem(Rock newRock, int userId) throws IOException;

    /**
     * Removes {@linkplain Rock remRock} from the cart
     *
     * @param remRock {@linkplain Rock remRock} id of the rock to be deleted
     *  
     * @param userId {@linkplain int userId} id of the user's cart that
     * the rock will be deleted from
     *
     * @return updated {@link Cart cart} with matching id to userId
     *
     * @throws IOException if an issue with underlying storage
     */
    public Cart deleteItem(Rock remRock, int userId) throws IOException;

    /**
     * Adds and saves a {@linkplain int rockId} to the cart
     *
     * @param rockId {@linkplain int rockId} id of the rock to be deleted
     *  
     * @param userId {@linkplain int userId} id of the user's cart that
     * the rock will be deleted from
     *
     * @return updated {@link Cart cart} with matching id to userId
     *
     * @throws IOException if an issue with underlying storage
     */
    Cart addCart(int id) throws IOException;

    /**
     * Retrieves all {@linkplain Rock rocks}
     *
     * @return An array of {@link Rock rock} objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    public Rock[] getRocksFromCart(Cart cart) throws IOException;

    /**
     * Retrieves all {@linkplain Cart carts}
     *
     * @return An array of {@link Cart cart} objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    Cart[] getCarts() throws IOException;

    /**
     * Retrieves the {@linkplain Cart cart} of the specified id and deletes every item in that cart.
     *
     * @param id The id of the cart to be cleared
     * 
     * @return The cleared {@link Cart cart}
     *
     * @throws IOException if an issue occurs with underlying storage
     */
    Cart clearCart(int id) throws IOException;


}
